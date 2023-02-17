package com.kappdev.wordbook.core.presentation.components

import android.view.Gravity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider

@Composable
fun DialogWithGravity(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    gravity: Int = Gravity.CENTER,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        val dialogWindowProvider = LocalView.current.parent as? DialogWindowProvider
        dialogWindowProvider?.window?.setGravity(gravity)

        content()
    }
}