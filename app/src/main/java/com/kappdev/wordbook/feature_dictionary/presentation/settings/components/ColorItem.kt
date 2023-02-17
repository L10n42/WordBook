package com.kappdev.wordbook.feature_dictionary.presentation.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ColorItem(
    modifier: Modifier = Modifier,
    color: Color,
    size: Dp = 32.dp,
    onClick: () -> Unit
) {
    Spacer(
        modifier = Modifier
            .size(size = size)
            .background(shape = CircleShape, color = color)
            .clickable { onClick() }
            .then(modifier)
    )
}