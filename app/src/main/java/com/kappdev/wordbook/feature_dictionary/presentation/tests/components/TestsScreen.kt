package com.kappdev.wordbook.feature_dictionary.presentation.tests.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.core.presentation.learn_components.LearnTopBar
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components.BackCard
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components.FinalCard
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components.FrontCard
import com.kappdev.wordbook.feature_dictionary.presentation.tests.TestsOptionsViewModel
import com.kappdev.wordbook.feature_dictionary.presentation.tests.TestsViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun TestsScreen(
    navController: NavHostController,
    viewModel: TestsViewModel = hiltViewModel(),
    optionsViewModel: TestsOptionsViewModel = hiltViewModel()
) {
    val progress = viewModel.progress.value
    val cards = viewModel.unstudiedCards
    val finished = progress.unstudied == 0 && progress.studied != 0

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val cardModifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(all = 16.dp)
        .background(
            color = MaterialTheme.colors.surface,
            shape = RoundedCornerShape(8.dp)
        )
        .padding(all = 16.dp)

    LaunchedEffect(key1 = true) {
        val setsIds = navController.previousBackStackEntry?.savedStateHandle?.get<List<String>>("setsIds")
        if (setsIds != null && setsIds.isNotEmpty())
            viewModel.launch(setsIds)
        else navController.popBackStack()
    }

    ModalBottomSheetLayout(
        sheetState =  bottomSheetState,
        scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.16f),
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetContent = {
            TestsOptionsBottomSheet(optionsViewModel) {
                viewModel.shuffleUnstudied()
            }
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
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                cards.take(1).forEach { card ->

                    if (!viewModel.isCurrentCard(card)) {
                        viewModel.writeCurrentCard(card)
                        viewModel.generateAnswers()
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colors.background)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (optionsViewModel.reversedReview.value) {
                            FrontCard(
                                item = card,
                                modifier = cardModifier,
                                say = { text -> viewModel.say(text) }
                            )
                        } else {
                            BackCard(
                                item = card,
                                isExampleVisible = optionsViewModel.isExamplesVisible.value,
                                isImageVisible = optionsViewModel.isImageVisible.value,
                                imageHeight = 100.dp,
                                definitionFontSize = 16.sp,
                                modifier = cardModifier
                            )
                        }

                        AnswerFields(
                            viewModel = viewModel,
                            reversedReview = optionsViewModel.reversedReview.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    }
                }

                if (finished) {
                    FinalCard(
                        Modifier
                            .fillMaxSize()
                            .padding(all = 16.dp)
                    ) { viewModel.studyAgain() }
                }
            }
        }
    }
}