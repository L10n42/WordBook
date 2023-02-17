package com.kappdev.wordbook.feature_dictionary.presentation.animation.shimmer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerSetListItem(brush: Brush) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 2.dp)
        .background(color = MaterialTheme.colors.surface)
        .padding(all = 16.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .height(16.dp)
                .clip(CircleShape)
                .background(brush = brush)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth(.5f)
                .height(16.dp)
                .clip(CircleShape)
                .background(brush = brush)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth(.3f)
                .height(16.dp)
                .clip(CircleShape)
                .background(brush = brush)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth(.45f)
                .height(32.dp)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 16.dp,
                        bottomStart = 0.dp,
                        topStart = 16.dp,
                        topEnd = 0.dp
                    )
                )
                .background(brush = brush)
        )
    }
}