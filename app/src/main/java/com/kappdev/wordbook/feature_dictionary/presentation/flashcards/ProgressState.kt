package com.kappdev.wordbook.feature_dictionary.presentation.flashcards

data class ProgressState(
    val all: Int = 0,
    val studied: Int = 0,
    val unstudied: Int = 0,
    val progressValue: Float = 0f
)
