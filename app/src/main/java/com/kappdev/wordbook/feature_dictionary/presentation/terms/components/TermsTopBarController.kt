package com.kappdev.wordbook.feature_dictionary.presentation.terms.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.AnimatedSearchBar
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermViewModel
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermsTopBar

@Composable
fun TermsTopBarController(
    title: String,
    currentTopBar: TermsTopBar,
    viewModel: TermViewModel,
    navController: NavHostController,
    openOrderSheet: () -> Unit
) {
    if (currentTopBar == TermsTopBar.Default) {
        TermsTopBar(
            title = title,
            onSearch = { viewModel.setTopBar(TermsTopBar.Search) },
            onSort = openOrderSheet,
            onBackPress = { navController.navigate(Screen.Sets.route) { popUpTo(0) } }
        )
    }

    AnimatedSearchBar(
        isVisible = currentTopBar == TermsTopBar.Search,
        placeHolder = stringResource(id = R.string.label_terms_search),
        onSearch = { searchArg -> viewModel.search(searchArg) },
        onCancelBtnClick = { viewModel.setTopBar(TermsTopBar.Default) }
    )

    AnimatedTermsMultiSelectBar(
        isVisible = currentTopBar == TermsTopBar.MultiSelect,
        viewModel = viewModel
    )
}