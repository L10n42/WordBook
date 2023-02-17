package com.kappdev.wordbook.feature_dictionary.presentation.tests

import android.content.Context

class TestsOptions(val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

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

    fun setReversedReview(value: Boolean) {
        editor.putBoolean(REVERSED_REVIEW, value).apply()
    }

    fun getReversedReview() : Boolean {
        return sharedPreferences.getBoolean(REVERSED_REVIEW, false)
    }

    private companion object {
        const val NAME  = "tests_options"

        const val IMAGE_VISIBILITY = "image_visibility"
        const val EXAMPLES_VISIBILITY = "examples_visibility"
        const val REVERSED_REVIEW = "reversed_review"
    }
}