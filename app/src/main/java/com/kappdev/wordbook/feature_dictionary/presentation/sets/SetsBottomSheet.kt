package com.kappdev.wordbook.feature_dictionary.presentation.sets

import com.kappdev.wordbook.feature_dictionary.domain.model.Set

sealed class SetsBottomSheet {
    object Order: SetsBottomSheet()
    class More(val set: Set): SetsBottomSheet()
}
