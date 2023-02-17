package com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term

data class FieldsState(
    val term: String = "",
    val transcription: String = "",
    val definition: String = "",
    val example: String = "",
    val image: String? = null,
)
