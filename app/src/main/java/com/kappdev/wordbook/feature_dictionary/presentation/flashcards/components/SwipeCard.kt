package com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.FlashCardSwipeDirection
import com.kappdev.wordbook.ui.theme.Amber500
import com.kappdev.wordbook.ui.theme.Green500
import kotlin.math.abs

@Composable
fun SwipeCard(
    onSwipeComplete: (FlashCardSwipeDirection) -> Unit,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = with(density) {
        configuration.screenWidthDp.dp.roundToPx()
    }

    var offsetX by remember {
        mutableStateOf(0f)
    }
    var offsetY by remember {
        mutableStateOf(0f)
    }

    val cardRotation = (offsetX / 60).coerceIn(-40f, 40f) * -1
    val dragLimit = screenWidth * 0.3f
    val dragIndicatorAlpha = abs(offsetX) / dragLimit

    Card(
        backgroundColor = Color.Transparent,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(
                translationX = offsetX,
                translationY = offsetY,
                rotationZ = cardRotation
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    },
                    onDragEnd = {
                        if (abs(offsetX) > dragLimit) {
                            val endPoint = screenWidth.toFloat() * 2
                            offsetX = if (offsetX < 0) -endPoint else endPoint
                            offsetY = if (offsetY < 0) -endPoint else endPoint

                            when {
                                (offsetX > 0) -> {
                                    onSwipeComplete(FlashCardSwipeDirection.RIGHT)
                                }
                                (offsetX < 0) -> {
                                    onSwipeComplete(FlashCardSwipeDirection.LEFT)
                                }
                            }
                        }
                        offsetX = 0f
                        offsetY = 0f
                    }
                )
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            content()

            when {
                (offsetX > 0) -> {
                    CardDragIndicator(
                        alpha = dragIndicatorAlpha,
                        color = Green500,
                        textId = R.string.flashcard_drag_title_got_it,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
                (offsetX < 0) -> {
                    CardDragIndicator(
                        alpha = dragIndicatorAlpha,
                        color = Amber500,
                        textId = R.string.flashcard_drag_title_study_again,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}