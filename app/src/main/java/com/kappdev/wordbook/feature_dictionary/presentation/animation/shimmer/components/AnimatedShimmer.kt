package com.kappdev.wordbook.feature_dictionary.presentation.animation.shimmer.components

import androidx.compose.animation.core.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.kappdev.wordbook.feature_dictionary.presentation.animation.shimmer.ShimmerColors

@Composable
fun AnimatedShimmer(
    colors: List<Color>? = null,
    content: @Composable (Brush) -> Unit
) {
    val isLightTheme = MaterialTheme.colors.isLight
    val lColors = when {
        colors != null -> colors
        isLightTheme -> ShimmerColors.DarkGray.colors
        else -> ShimmerColors.LightGray.colors
    }

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 700,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = lColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    content(brush)
}