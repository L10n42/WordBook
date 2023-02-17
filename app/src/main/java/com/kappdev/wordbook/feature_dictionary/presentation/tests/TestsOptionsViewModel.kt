package com.kappdev.wordbook.feature_dictionary.presentation.tests

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestsOptionsViewModel @Inject constructor(
    private val testsOptions: TestsOptions
) : ViewModel() {

    private val _isImageVisible = mutableStateOf(true)
    val isImageVisible: State<Boolean> = _isImageVisible

    private val _isExamplesVisible = mutableStateOf(true)
    val isExamplesVisible: State<Boolean> = _isExamplesVisible

    private val _reversedReview = mutableStateOf(false)
    val reversedReview: State<Boolean> = _reversedReview

    init {
        _isImageVisible.value = testsOptions.getImageVisibility()
        _isExamplesVisible.value = testsOptions.getExamplesVisibility()
        _reversedReview.value = testsOptions.getReversedReview()
    }

    fun setImageVisibility(isVisible: Boolean) {
        _isImageVisible.value = isVisible
        testsOptions.setImageVisibility(isVisible)
    }

    fun setExamplesVisibility(isVisible: Boolean) {
        _isExamplesVisible.value = isVisible
        testsOptions.setExamplesVisibility(isVisible)
    }

    fun setReversedReview(reversedReview: Boolean) {
        _reversedReview.value = reversedReview
        testsOptions.setReversedReview(reversedReview)
    }
}