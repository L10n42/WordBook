package com.kappdev.wordbook.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup

@Composable
fun SpeakIconWithPopupTranscription(
    modifier: Modifier = Modifier,
    text: String,
    onIconClick: () -> Unit,
) {
    Box(
        modifier = modifier.wrapContentSize()
    ) {
        var iconSize by remember { mutableStateOf(Size.Zero) }
        val offsetX = -(iconSize.height.toInt())
        val offsetY = -(iconSize.height * 1.5).toInt()

        var showTranscription by remember { mutableStateOf(false) }
        if (showTranscription && text.isNotBlank()) {
            TranscriptionPopup(
                message = text,
                offset = IntOffset(offsetX, offsetY),
                closePopup = { showTranscription = false },
            )
        } else {
            if (showTranscription) showTranscription = false
        }

        IconButton(
            onClick = {
                onIconClick()
                showTranscription = true
            }
        ) {
            Icon(
                imageVector = Icons.Default.VolumeUp,
                contentDescription = "volume_up_icon",
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    iconSize = coordinates.size.toSize()
                }
            )
        }
    }
}

@Composable
private fun TranscriptionPopup(
    message: String,
    alignment: Alignment = DefaultAlignment,
    offset: IntOffset = DefaultOffset,
    shape: Shape = DefaultShape,
    closePopup: () -> Unit
) {
    Popup(
        alignment = alignment,
        offset = offset,
        onDismissRequest = closePopup
    ) {
        Surface(
            color = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface,
            elevation = 4.dp,
            shape = shape
        ) {
            Text(
                text = message,
                fontSize = 16.sp,
                modifier = Modifier.padding(all = 8.dp)
            )
        }
    }
}

private val DefaultOffset = IntOffset(0, 0)
private val DefaultAlignment = Alignment.BottomEnd
private val DefaultShape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp, bottomStart = 8.dp, bottomEnd = 0.dp)