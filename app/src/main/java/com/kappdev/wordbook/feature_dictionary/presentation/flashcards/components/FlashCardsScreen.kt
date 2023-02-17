package com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.core.presentation.components.LoadingDialog
import com.kappdev.wordbook.core.presentation.learn_components.LearnTopBar
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.FlashCardsViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun FlashCardsScreen(
    navController: NavHostController,
    flashCardsViewModel: FlashCardsViewModel = hiltViewModel()
) {
    val isLoading = flashCardsViewModel.isLoading.value
    val progress = flashCardsViewModel.progress.value
    val elements = flashCardsViewModel.unstudiedCards
    val finished = progress.unstudied == 0 && progress.studied != 0

    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        val setsIds = navController.previousBackStackEntry?.savedStateHandle?.get<List<String>>("setsIds")
        if (setsIds != null && setsIds.isNotEmpty())
            flashCardsViewModel.launch(setsIds)
        else navController.popBackStack()
    }

    ModalBottomSheetLayout(
        sheetState =  bottomSheetState,
        scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.16f),
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetContent = {
            FlashCardsOptionsSection(flashCardsViewModel)
        }
    ) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                LearnTopBar(
                    progress = progress,
                    onCloseClick = {
                        navController.navigate(Screen.Sets.route){ popUpTo(0) }
                    },
                    onSettingClick = {
                        scope.launch { bottomSheetState.show() }
                    }
                )
            }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .fillMaxHeight(0.85f)
                ) {
                    if(elements.isNotEmpty()) {
                        CardStack(viewModel = flashCardsViewModel)
                    }

                    if (finished) {
                        FinalCard(Modifier.fillMaxSize()) { flashCardsViewModel.reset() }
                    }
                }
            }

            if (isLoading) LoadingDialog()
        }
    }
}