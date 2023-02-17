package com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.CardFace
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.CardRotation

@ExperimentalMaterialApi
@Composable
fun FlipCard(
    modifier: Modifier = Modifier,
    reversedReview: Boolean,
    cardRotation: CardRotation = CardRotation.Horizontal,
    back: @Composable () -> Unit,
    front: @Composable () -> Unit
) {
    val defaultCardFace = if (reversedReview) CardFace.Back else CardFace.Front
    var cardFace by rememberSaveable {
        mutableStateOf(defaultCardFace)
    }

    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        )
    )

    Card(
        onClick = { cardFace = cardFace.next },
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer {
                if (cardRotation == CardRotation.Horizontal) {
                    rotationY = rotation.value
                } else
                    rotationX = rotation.value
                cameraDistance = 10f * density
            }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (rotation.value <= 90f)
                Box(
                    modifier = Modifier.fillMaxSize()
                ) { front() }
            else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            if (cardRotation == CardRotation.Horizontal)
                                rotationY = 180f
                            else
                                rotationX = 180f
                        }
                ) { back() }
            }
        }
    }
}