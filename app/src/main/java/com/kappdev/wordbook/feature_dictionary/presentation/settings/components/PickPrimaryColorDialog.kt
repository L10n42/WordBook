package com.kappdev.wordbook.feature_dictionary.presentation.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.ColorPickerDialog
import com.kappdev.wordbook.feature_dictionary.presentation.settings.SettingsViewModel
import com.kappdev.wordbook.ui.theme.MaterialColors200
import com.kappdev.wordbook.ui.theme.MaterialColors500

@Composable
fun PickPrimaryColorDialog(
    onColorChoose: (color: Color) -> Unit,
    hideDialog: () -> Unit,
    showColorPicker: () -> Unit
) {
    val isThemeLight = MaterialTheme.colors.isLight
    val colors = if (isThemeLight) MaterialColors500 else MaterialColors200

    Dialog(onDismissRequest = hideDialog) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colors.background,
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(all = 16.dp),
                columns = GridCells.Fixed(count = 4),
            ) {
                items(colors) { color ->
                    GridItem {
                        ColorItem(color = color, size = 56.dp) {
                            onColorChoose(color)
                            hideDialog()
                        }
                    }
                }
                item {
                    ColorWheelItem {
                        hideDialog()
                        showColorPicker()
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorWheelItem(onClick: () -> Unit) {
    GridItem {
        Image(
            painter = painterResource(id = R.drawable.color_wheel),
            contentDescription = "color wheel item",
            modifier = Modifier
                .size(56.dp)
                .clickable { onClick() }
        )
    }
}

@Composable
private fun GridItem(content: @Composable BoxScope.() -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(64.dp),
        content = content
    )
}