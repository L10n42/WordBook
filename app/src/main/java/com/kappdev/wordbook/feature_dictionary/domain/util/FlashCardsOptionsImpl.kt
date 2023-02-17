package com.kappdev.wordbook.feature_dictionary.domain.util

import android.content.Context
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.CardRotation
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.FlashCardsOptions

class FlashCardsOptionsImpl(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(OPTIONS, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun getAllOptions() : FlashCardsOptions {
        return FlashCardsOptions(
            isImageVisible = getImageVisibility(),
            isExampleVisible = getExamplesVisibility(),
            cardRotation = getCardRotation(),
            reversedReview = getReversedReview()
        )
    }

    fun setImageVisibility(value: Boolean) {
        editor.putBoolean(IMAGE_VISIBILITY, value).apply()
    }

    fun getImageVisibility() : Boolean {
        return sharedPreferences.getBoolean(IMAGE_VISIBILITY, true)
    }

    fun setExamplesVisibility(value: Boolean) {
        editor.putBoolean(EXAMPLES_VISIBILITY, value).apply()
    }

    fun getExamplesVisibility() : Boolean {
        return sharedPreferences.getBoolean(EXAMPLES_VISIBILITY, true)
    }

    fun setCardRotation(value: CardRotation) {
        editor.putString(CARD_ROTATION, value.id).apply()
    }

    fun getCardRotation() : CardRotation {
        val rotationId = sharedPreferences.getString(CARD_ROTATION, CardRotation.Horizontal.id)
        return when(rotationId) {
            CardRotation.Horizontal.id -> CardRotation.Horizontal
            CardRotation.Vertical.id -> CardRotation.Vertical
            else -> CardRotation.Horizontal
        }
    }

    fun setReversedReview(value: Boolean) {
        editor.putBoolean(REVERSED_REVIEW, value).apply()
    }

    fun getReversedReview() : Boolean {
        return sharedPreferences.getBoolean(REVERSED_REVIEW, false)
    }

    private companion object {
        const val OPTIONS = "flashcard_options"

        const val IMAGE_VISIBILITY = "image_visibility"
        const val EXAMPLES_VISIBILITY = "examples_visibility"
        const val CARD_ROTATION = "card_rotation"
        const val REVERSED_REVIEW = "reversed_review"
    }
}