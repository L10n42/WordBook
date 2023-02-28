package com.kappdev.wordbook.feature_dictionary.presentation.sets.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.*
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsTopBar
import com.kappdev.wordbook.feature_dictionary.presentation.animation.shimmer.components.AnimatedShimmer
import com.kappdev.wordbook.feature_dictionary.presentation.animation.shimmer.components.ShimmerSetListItem
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsBottomSheet
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsDialog
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsViewModel
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun SetsScreen(
    navController: NavHostController,
    viewModel: SetsViewModel = hiltViewModel()
) {
    val currentTopBar = viewModel.currentTopBar.value
    val isListLoading = viewModel.isListLoading.value
    val snackbarState = viewModel.snackbarState.value
    val isSearchModeOn = viewModel.currentTopBar.value == SetsTopBar.Search
    val isMultiSelectModeOn = viewModel.currentTopBar.value == SetsTopBar.MultiSelect
    val currentList = when {
        isSearchModeOn -> viewModel.setsSearchList
        else -> viewModel.sets
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var scrollingToTop by remember { mutableStateOf(true) }

    var currentBottomSheet by remember { mutableStateOf<SetsBottomSheet?>(null) }
    val closeSheet: () -> Unit = {
        scope.launch { bottomSheetState.hide() }
    }
    val openSheet: (SetsBottomSheet) -> Unit = { bottomSheet ->
        currentBottomSheet = bottomSheet
        scope.launch { bottomSheetState.show() }
    }

    val currentDialog = viewModel.currentDialog.value
    currentDialog?.let { dialog ->
        SetsDialogController(
            currentDialog = dialog,
            viewModel = viewModel
        )
    }

    if(snackbarState.isVisible) {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = snackbarState.message,
                duration = SnackbarDuration.Short
            )
        }
        viewModel.setSnackbarVisibility(false)
    }

    if(!bottomSheetState.isVisible) currentBottomSheet = null
    ModalBottomSheetLayout(
        sheetState =  bottomSheetState,
        scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.16f),
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetContent = {
            if (currentBottomSheet != null) {
                currentBottomSheet?.let { currentSheet ->
                    SetSheetsController(
                        viewModel = viewModel,
                        currentScreen = currentSheet,
                        onCloseBS = closeSheet,
                        navController = navController
                    )
                }
            } else Box(Modifier.height(1.dp))
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState },
            topBar = {
                SetsTopBarController(currentTopBar, viewModel, navController) {
                    openSheet(SetsBottomSheet.Order)
                }
            },
            floatingActionButton = {
                AnimatedAddBtn(
                    expanded = scrollingToTop,
                    isVisible = !isSearchModeOn && !isMultiSelectModeOn,
                    textId = R.string.btn_create_set,
                    onClick = { viewModel.showDialog(SetsDialog.Add) }
                )
            }
        ) {
            val isListEmpty = !isSearchModeOn && viewModel.isListEmpty.value
            val isSearchListEmpty = isSearchModeOn && currentList.isEmpty() && viewModel.lastSearchValue.trim().isNotEmpty()

            val list: @Composable () -> Unit = {
                LazyColumnWithScrollIndicator(
                    verticalArrangement = Arrangement.spacedBy(ListItemsPadding),
                    contentPadding = PaddingValues(vertical = ListItemsPadding),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.background),
                    onScroll = { direction ->
                        scrollingToTop = when (direction) {
                            LazyColumnScrollDirection.TOP -> true
                            LazyColumnScrollDirection.BOTTOM -> false
                        }
                    }
                ) {

                    items(currentList) { set ->
                        SetCard(
                            set = set,
                            navController = navController,
                            viewModel = viewModel,
                            showMoreSheet = {
                                openSheet(SetsBottomSheet.More(set))
                            }
                        )
                    }
                }
            }

            when {
                isListLoading -> LoadingListScreen()
                isSearchListEmpty -> UnableToFind()
                isListEmpty -> EmptyListScreen(R.string.empty_sets_list_msg)
                currentList.isNotEmpty() || isSearchModeOn -> list()
                else -> LoadingListScreen()
            }

            Box(modifier = Modifier.fillMaxSize()) {
                CustomSnackbar(
                    snackbarHostState = scaffoldState.snackbarHostState,
                    type = snackbarState.snackbarType,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}

private val ListItemsPadding = 2.dp

@Composable
private fun LoadingListScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = 10) {
            AnimatedShimmer {
                ShimmerSetListItem(brush = it)
            }
        }
    }
}