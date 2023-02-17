package com.kappdev.wordbook.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CardSelectedEffect(
    isVisible: Boolean,
    width: Dp,
    height: Dp
) {
    val borderColor = MaterialTheme.colors.primary.copy(alpha = 0.7f)
    val backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f)

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        ),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        Spacer(
            modifier = Modifier
                .width(width)
                .height(height)
                .background(color = backgroundColor, shape = RoundedCornerShape(4.dp))
                .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(4.dp))
        )
    }
}