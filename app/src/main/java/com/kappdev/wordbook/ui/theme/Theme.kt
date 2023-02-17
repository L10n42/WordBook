package com.kappdev.wordbook.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Blue200,
    primaryVariant = Purple700,
    secondary = mTeal200,
    secondaryVariant = mTeal200,
    background = DarkGray,
    surface = LightGray,
    error = LiteErrorRed,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = White_60A,
    onSurface = White_87A,
    onError = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Blue500,
    primaryVariant = Purple700,
    secondary = mTeal200,
    secondaryVariant = DarkTeal,
    background = LiteWhite,
    surface = Color.White,
    error = HardErrorRed,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Black_60A,
    onSurface = Black_87A,
    onError = Color.White
)

@Composable
fun WordBookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    darkThemePrimaryColor: Color = DarkColorPalette.primary,
    lightThemePrimaryColor: Color = LightColorPalette.primary,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette.copy(
            primary = darkThemePrimaryColor
        )
    } else {
        LightColorPalette.copy(
            primary = lightThemePrimaryColor
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}