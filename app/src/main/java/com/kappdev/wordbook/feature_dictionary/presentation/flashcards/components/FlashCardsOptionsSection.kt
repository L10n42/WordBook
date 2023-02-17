package com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.DefaultRadioButton
import com.kappdev.wordbook.core.presentation.components.OptionSwitcher
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.CardRotation
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.FlashCardsViewModel

@Composable
fun FlashCardsOptionsSection(
    viewModel: FlashCardsViewModel
) {
    val isImageVisible = viewModel.options.value.isImageVisible
    val isExampleVisible = viewModel.options.value.isExampleVisible
    val cardRotation = viewModel.options.value.cardRotation
    val reversedReview = viewModel.options.value.reversedReview

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
                onCheckedChange = { viewModel.switchReversCard() }
            )
            OptionSwitcher(
                textId = R.string.flashcard_option_is_image_visible,
                isChecked = isImageVisible,
                onCheckedChange = { viewModel.switchImageVisibility() }
            )
            OptionSwitcher(
                textId = R.string.flashcard_option_is_examples_visible,
                isChecked = isExampleVisible,
                onCheckedChange = { viewModel.switchExampleVisibility() }
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.flashcard_option_card_rotation),
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSurface
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    DefaultRadioButton(
                        text = stringResource(id = R.string.flashcard_option_card_rotation_horizontal),
                        selected = cardRotation == CardRotation.Horizontal,
                        onSelect = {
                            viewModel.switchCardRotation()
                        }
                    )
                    DefaultRadioButton(
                        text = stringResource(id = R.string.flashcard_option_card_rotation_vertical),
                        selected = cardRotation == CardRotation.Vertical,
                        onSelect = {
                            viewModel.switchCardRotation()
                        }
                    )
                }
            }
        }
    }
}