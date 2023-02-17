package com.kappdev.wordbook.feature_dictionary.presentation.flashcards

sealed class CardRotation(val id: String) {
    object Horizontal: CardRotation("horizontal_rotation")
    object Vertical: CardRotation("vertical_rotation")
}