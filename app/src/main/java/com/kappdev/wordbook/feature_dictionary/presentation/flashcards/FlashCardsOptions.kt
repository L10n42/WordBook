package com.kappdev.wordbook.feature_dictionary.presentation.flashcards

data class FlashCardsOptions(
    val isImageVisible: Boolean = true,
    val isExampleVisible: Boolean = true,
    val cardRotation: CardRotation = CardRotation.Horizontal,
    val reversedReview: Boolean = false
)
