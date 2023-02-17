package com.kappdev.wordbook.feature_dictionary.presentation.sets.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.AnimatedSearchBar
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsTopBar
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsViewModel

@Composable
fun SetsTopBarController(
    topBar: SetsTopBar,
    viewModel: SetsViewModel,
    navController: NavHostController,
    openSheet: () -> Unit
) {

    SetsTopBar(
        onSort = openSheet,
        onSearch = { viewModel.setTopBar(SetsTopBar.Search) },
        onSettings = { navController.navigate(Screen.Settings.route) { popUpTo(0) } }
    )

    AnimatedSetsMultiSelectBar(
        isVisible = topBar == SetsTopBar.MultiSelect,
        navController = navController,
        viewModel = viewModel
    )

    AnimatedSearchBar(
        isVisible = topBar == SetsTopBar.Search,
        placeHolder = stringResource(id = R.string.label_sets_search),
        onSearch = { searchArg -> viewModel.search(searchArg) },
        onCancelBtnClick = { viewModel.setTopBar(SetsTopBar.Default) },
    )
}