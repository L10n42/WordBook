package com.kappdev.wordbook.feature_dictionary.domain.util

data class SnackbarState(
    val isVisible: Boolean = false,
    val message: String = "",
    val snackbarType: SnackbarType = SnackbarType.Info
)
