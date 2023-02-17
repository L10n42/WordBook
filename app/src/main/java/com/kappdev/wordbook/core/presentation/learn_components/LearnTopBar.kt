package com.kappdev.wordbook.core.presentation.learn_components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.ProgressState

@Composable
fun LearnTopBar(
    modifier: Modifier = Modifier,
    progress: ProgressState,
    onCloseClick: () -> Unit = {},
    onSettingClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth().then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close_icon",
                    tint = MaterialTheme.colors.onSurface
                )
            }
            IconButton(onClick = onSettingClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "settings_icon",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }

        ProgressSection(
            modifier = Modifier.fillMaxWidth(0.85f),
            progressState = progress
        )
    }
}

@Composable
private fun ProgressSection(
    modifier: Modifier = Modifier,
    progressState: ProgressState
) {
    val text = "${progressState.studied}/${progressState.all}"

    val animatedProgress by animateFloatAsState(
        targetValue = progressState.progressValue,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = text,
            fontSize = 18.sp,
            color = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = animatedProgress,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape)
        )
    }
}