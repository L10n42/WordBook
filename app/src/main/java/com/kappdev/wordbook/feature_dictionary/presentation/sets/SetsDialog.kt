package com.kappdev.wordbook.feature_dictionary.presentation.sets

import com.kappdev.wordbook.feature_dictionary.domain.model.Set

sealed class SetsDialog {
    class Remove(val set: Set): SetsDialog()
    class Edit(val set: Set): SetsDialog()
    object Add: SetsDialog()
}
