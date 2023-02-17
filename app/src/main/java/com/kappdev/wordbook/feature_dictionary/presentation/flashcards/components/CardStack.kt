package com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.FlashCardSwipeDirection
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.FlashCardsViewModel

@ExperimentalMaterialApi
@Composable
fun CardStack(
    viewModel: FlashCardsViewModel
) {
    val elements = viewModel.unstudiedCards
    val options = viewModel.options.value

    elements.take(2).reversed().forEach { card ->
        key(card) {
            SwipeCard(
                onSwipeComplete = { swipeDirection ->
                    when (swipeDirection) {
                        FlashCardSwipeDirection.RIGHT -> {
                            viewModel.moveCardToStudied(card)
                        }
                        FlashCardSwipeDirection.LEFT -> {
                            viewModel.switchCard(card)
                        }
                    }
                }
            ) {
                FlipCard(
                    reversedReview = options.reversedReview,
                    modifier = Modifier.fillMaxSize(),
                    cardRotation = options.cardRotation,
                    back = {
                        BackCard(
                            item = card,
                            isExampleVisible = options.isExampleVisible,
                            isImageVisible = options.isImageVisible,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 16.dp)
                                .verticalScroll(rememberScrollState())
                        )
                    },
                    front = {
                        FrontCard(
                            item = card,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 16.dp)
                                .verticalScroll(rememberScrollState()),
                            say = { term ->
                                viewModel.say(text = term)
                            }
                        )
                    }
                )
            }
        }
    }
}