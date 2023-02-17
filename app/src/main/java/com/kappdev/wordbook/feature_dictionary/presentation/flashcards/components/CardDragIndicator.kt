package com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardDragIndicator(
    modifier: Modifier = Modifier,
    alpha: Float,
    color: Color,
    textId: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .alpha(alpha)
            .then(modifier)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(color = color)
        )

        Text(
            text = stringResource(id = textId),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}