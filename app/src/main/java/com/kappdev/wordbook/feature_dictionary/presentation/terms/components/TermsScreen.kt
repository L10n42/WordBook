package com.kappdev.wordbook.feature_dictionary.presentation.terms.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermsTopBar
import com.kappdev.wordbook.core.presentation.components.*
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.feature_dictionary.presentation.animation.shimmer.components.AnimatedShimmer
import com.kappdev.wordbook.feature_dictionary.presentation.animation.shimmer.components.ShimmerTermListItem
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermViewModel
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermsBottomSheet
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun TermsScreen(
    navController: NavHostController,
    termViewModel: TermViewModel = hiltViewModel()
) {
    val currentTopBar = termViewModel.currentTopBar.value
    val isMultiSelectModeOn = currentTopBar == TermsTopBar.MultiSelect
    val isSearchModeOn = currentTopBar == TermsTopBar.Search
    val currentSet = termViewModel.currentSet.value
    val currentList = when {
        isSearchModeOn -> termViewModel.cardTermSearchList
        else -> termViewModel.cardTerms
    }

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var scrollingToTop by remember { mutableStateOf(true) }

    var currentBottomSheet by remember { mutableStateOf<TermsBottomSheet?>(null) }
    val closeSheet: () -> Unit = {
        scope.launch { bottomSheetState.hide() }
    }
    val openSheet: (TermsBottomSheet) -> Unit = { bottomSheet ->
        currentBottomSheet = bottomSheet
        scope.launch { bottomSheetState.show() }
    }

    val currentDialog = termViewModel.currentDialog.value
    currentDialog?.let { dialog ->
        TermsDialogsController(
            currentDialog = dialog,
            viewModel = termViewModel
        )
    }

    LaunchedEffect(key1 = true) {
        termViewModel.onLaunch()
    }

    if(!bottomSheetState.isVisible) currentBottomSheet = null
    currentSet?.let { set ->
        ModalBottomSheetLayout(
            sheetState =  bottomSheetState,
            scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.16f),
            sheetBackgroundColor = Color.Transparent,
            sheetElevation = 0.dp,
            sheetContent = {
                if (currentBottomSheet != null) {
                    currentBottomSheet?.let { currentSheet ->
                        TermsSheetsController(
                            viewModel = termViewModel,
                            currentSheet = currentSheet,
                            onCloseBS = closeSheet,
                            navController = navController
                        )
                    }
                } else Box(modifier = Modifier.height(1.dp))
            }
        ) {
            Scaffold(
                topBar = {
                    TermsTopBarController(
                        title = set.name,
                        currentTopBar = currentTopBar,
                        viewModel = termViewModel,
                        navController = navController,
                        openOrderSheet = { openSheet(TermsBottomSheet.Order) }
                    )
                },
                floatingActionButton = {
                    AnimatedAddBtn(
                        textId = R.string.btn_create_term,
                        expanded = scrollingToTop,
                        isVisible = !isSearchModeOn && !isMultiSelectModeOn,
                        onClick = { navController.navigate(Screen.AddEditTerm.route + "?setId=${set.setId}") }
                    )
                }
            ) {
                val isListEmpty = !isSearchModeOn && termViewModel.isListEmpty.value
                val isSearchListEmpty = isSearchModeOn && currentList.isEmpty() && termViewModel.lastSearchValue.trim().isNotEmpty()

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
                        items(currentList) { term ->
                            TermCard(
                                cardTerm = term,
                                viewModel = termViewModel,
                                showMoreSheet = { openSheet(TermsBottomSheet.More(term.term)) }
                            )
                        }
                    }
                }

                when {
                    termViewModel.isListLoading.value -> LoadingListScreen()
                    isSearchListEmpty -> UnableToFind()
                    isListEmpty -> EmptyListScreen(R.string.empty_terms_list_msg)
                    currentList.isNotEmpty() || isSearchModeOn -> list()
                    else -> LoadingListScreen()
                }
            }
        }
    }
}

private val ListItemsPadding = 4.dp

@Composable
private fun LoadingListScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = 10) {
            AnimatedShimmer {
                ShimmerTermListItem(brush = it)
            }
        }
    }
}