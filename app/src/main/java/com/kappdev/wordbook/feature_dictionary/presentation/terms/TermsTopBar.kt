package com.kappdev.wordbook.feature_dictionary.presentation.terms

sealed class TermsTopBar {
    object Search: TermsTopBar()
    object MultiSelect: TermsTopBar()
    object Default: TermsTopBar()
}