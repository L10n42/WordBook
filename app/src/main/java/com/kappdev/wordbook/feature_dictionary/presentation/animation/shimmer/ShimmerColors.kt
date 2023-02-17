package com.kappdev.wordbook.feature_dictionary.presentation.animation.shimmer

import androidx.compose.ui.graphics.Color
import com.kappdev.wordbook.ui.theme.White_60A
import com.kappdev.wordbook.ui.theme.White_87A

sealed class ShimmerColors(val colors: List<Color>) {
    object DarkGray: ShimmerColors(
        colors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
        )
    )
    object LightGray: ShimmerColors(
        colors = listOf(
            White_60A,
            White_87A,
            White_60A,
        )
    )
}
