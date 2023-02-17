package com.kappdev.wordbook.feature_dictionary.presentation.sets

sealed class SetsTopBar {
    object Search: SetsTopBar()
    object MultiSelect: SetsTopBar()
    object Default: SetsTopBar()
}
