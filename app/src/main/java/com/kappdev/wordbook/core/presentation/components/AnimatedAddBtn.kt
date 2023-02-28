package com.kappdev.wordbook.core.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedAddBtn(
    textId: Int,
    isVisible: Boolean,
    onClick: () -> Unit
) {
    val iconRotation by animateFloatAsState(
        targetValue = if (isVisible) 0f else 360f,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        )
    )
    val animatedWidth by animateDpAsState(
        targetValue = if (isVisible) 150.dp else 56.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        )
    )
    val animatedPadding by animateDpAsState(
        targetValue = if (isVisible) 0.dp else 16.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        )
    )
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.secondary,
        shape = CircleShape,
        modifier = Modifier
            .height(56.dp)
            .width(animatedWidth),
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            hoveredElevation = 16.dp,
            focusedElevation = 4.dp,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(animatedPadding))
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add_icon",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(end = 4.dp).rotate(iconRotation)
            )
            Spacer(modifier = Modifier.width(animatedPadding))
            Text(
                text = stringResource(id = textId),
                color = MaterialTheme.colors.onPrimary,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
