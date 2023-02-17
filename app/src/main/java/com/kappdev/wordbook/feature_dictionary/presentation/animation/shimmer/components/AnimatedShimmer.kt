package com.kappdev.wordbook.feature_dictionary.presentation.animation.shimmer.components

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.kappdev.wordbook.feature_dictionary.domain.util.SettingsSP
import com.kappdev.wordbook.feature_dictionary.presentation.animation.shimmer.ShimmerColors

@Composable
fun AnimatedShimmer(
    colors: List<Color>? = null,
    content: @Composable (Brush) -> Unit
) {
    val context = LocalContext.current
    val sp = context.getSharedPreferences(SettingsSP.Name.key, Context.MODE_PRIVATE)
    val isCurrentThemeDark = sp.getBoolean(SettingsSP.Theme.key, true)
    val lColors = when {
        colors != null -> colors
        isCurrentThemeDark -> ShimmerColors.LightGray.colors
        else -> ShimmerColors.DarkGray.colors
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