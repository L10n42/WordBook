package com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term


sealed class AddEditTermEvent {
    object ProposeMeaning: AddEditTermEvent()
    object UpdateTerm: AddEditTermEvent()
    object CreateTerm: AddEditTermEvent()
}
