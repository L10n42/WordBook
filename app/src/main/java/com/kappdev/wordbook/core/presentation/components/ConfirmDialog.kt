package com.kappdev.wordbook.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.kappdev.wordbook.R

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    dismissColor: Color = MaterialTheme.colors.primary,
    confirmColor: Color = MaterialTheme.colors.error,
    dismissText: String = stringResource(id = R.string.dialog_btn_dismiss),
    confirmText: String = stringResource(id = R.string.dialog_btn_confirm),
    closeDialog: () -> Unit,
    onDismiss: () -> Unit = closeDialog,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = closeDialog,
        shape = RoundedCornerShape(4.dp),
        backgroundColor = MaterialTheme.colors.background,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )
        },
        text = {
            Text(
                text = message,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onSurface
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = confirmText,
                    color = confirmColor,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = dismissText,
                    color = dismissColor,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                )
            }
        }
    )
}