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
fun ShimmerTermListItem(brush: Brush) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 2.dp, horizontal = 2.dp)
        .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(4.dp))
        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(.55f)
                .height(16.dp)
                .clip(CircleShape)
                .background(brush = brush)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row() {
            Spacer(
                modifier = Modifier
                    .height(75.dp)
                    .width(100.dp)
                    .background(brush = brush, shape = RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column() {
                Spacer(modifier = Modifier.height(4.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(.7f)
                        .height(16.dp)
                        .clip(CircleShape)
                        .background(brush = brush)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .height(16.dp)
                        .clip(CircleShape)
                        .background(brush = brush)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(16.dp)
                        .clip(CircleShape)
                        .background(brush = brush)
                )
            }
        }
    }
}