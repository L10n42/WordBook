package com.kappdev.wordbook.feature_dictionary.presentation.settings.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kappdev.wordbook.feature_dictionary.presentation.settings.SettingsViewModel
import com.kappdev.wordbook.ui.theme.MaterialColors200
import com.kappdev.wordbook.ui.theme.MaterialColors500

@Composable
fun PickPrimaryColorDialog(
    settingsViewModel: SettingsViewModel,
    setShowDialog: (Boolean) -> Unit
) {
    val isThemeDark = settingsViewModel.isThemeDark()
    val colors = if (isThemeDark) MaterialColors200 else MaterialColors500

    Dialog(
        onDismissRequest = {
            setShowDialog(false)
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colors.background,
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(all = 16.dp),
                columns = GridCells.Fixed(count = 4),
            ) {
                items(colors) { color ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(64.dp)
                    ) {
                        ColorItem(
                            color = color,
                            size = 56.dp
                        ) {
                            when {
                                isThemeDark -> settingsViewModel.setDarkThemePrimaryColor(color)
                                !isThemeDark -> settingsViewModel.setLightThemePrimaryColor(color)
                            }
                            setShowDialog(false)
                        }
                    }
                }
            }
        }
    }
}