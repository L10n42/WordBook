package com.kappdev.wordbook.feature_dictionary.presentation.writing

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WritingOptionsViewModel @Inject constructor(
    private val writingOptions: WritingOptions
) : ViewModel() {

    private val _isImageVisible = mutableStateOf(true)
    val isImageVisible: State<Boolean> = _isImageVisible

    private val _isExamplesVisible = mutableStateOf(true)
    val isExamplesVisible: State<Boolean> = _isExamplesVisible

    init {
        _isImageVisible.value = writingOptions.getImageVisibility()
        _isExamplesVisible.value = writingOptions.getExamplesVisibility()
    }

    fun setImageVisibility(isVisible: Boolean) {
        _isImageVisible.value = isVisible
        writingOptions.setImageVisibility(isVisible)
    }

    fun setExamplesVisibility(isVisible: Boolean) {
        _isExamplesVisible.value = isVisible
        writingOptions.setExamplesVisibility(isVisible)
    }
}