package com.kappdev.wordbook.feature_dictionary.presentation.sets

import com.kappdev.wordbook.feature_dictionary.domain.model.Set

sealed class SetsEvent {
    data class AddSet(val set: Set): SetsEvent()
    data class RemoveSet(val set: Set): SetsEvent()
    data class UpdateSet(val set: Set): SetsEvent()
}
