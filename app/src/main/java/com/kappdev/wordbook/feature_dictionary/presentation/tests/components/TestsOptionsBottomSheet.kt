package com.kappdev.wordbook.feature_dictionary.presentation.tests.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.OptionSwitcher
import com.kappdev.wordbook.feature_dictionary.presentation.tests.TestsOptionsViewModel

@Composable
fun TestsOptionsBottomSheet(
    viewModel: TestsOptionsViewModel,
    onChange: () -> Unit
) {
    val isImageVisible = viewModel.isImageVisible.value
    val isExampleVisible = viewModel.isExamplesVisible.value
    val reversedReview = viewModel.reversedReview.value

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OptionSwitcher(
                textId = R.string.flashcard_option_is_reversed_review,
                isChecked = reversedReview,
                onCheckedChange = {
                    viewModel.setReversedReview(!reversedReview)
                    onChange()
                }
            )
            OptionSwitcher(
                textId = R.string.flashcard_option_is_image_visible,
                isChecked = isImageVisible,
                onCheckedChange = {
                    viewModel.setImageVisibility(!isImageVisible)
                    onChange()
                }
            )
            OptionSwitcher(
                textId = R.string.flashcard_option_is_examples_visible,
                isChecked = isExampleVisible,
                onCheckedChange = {
                    viewModel.setExamplesVisibility(!isExampleVisible)
                    onChange()
                }
            )
        }
    }
}