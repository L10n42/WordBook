package com.kappdev.wordbook.feature_dictionary.presentation.writing

import android.content.Context

class WritingOptions(val context: Context) {

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

    private companion object {
        const val NAME  = "writing_options"

        const val IMAGE_VISIBILITY = "image_visibility"
        const val EXAMPLES_VISIBILITY = "examples_visibility"
    }
}