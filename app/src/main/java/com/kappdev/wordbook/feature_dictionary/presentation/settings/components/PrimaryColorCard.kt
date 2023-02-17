package com.kappdev.wordbook.feature_dictionary.presentation.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.presentation.settings.SettingsViewModel

@Composable
fun PrimaryColorCard(
    settingsViewModel: SettingsViewModel
) {
    val isThemeDark = settingsViewModel.isThemeDark()
    val currentColor = when {
        isThemeDark -> settingsViewModel.getDarkThemePrimaryColor()
        else -> settingsViewModel.getLightThemePrimaryColor()
    }

    var showPickColorDialog by remember {
        mutableStateOf(false)
    }

    if (showPickColorDialog)
        PickPrimaryColorDialog(
            settingsViewModel = settingsViewModel,
            setShowDialog = { hide -> showPickColorDialog = hide }
        )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.settings_title_theme_primary_color),
            fontSize = 18.sp,
            color = MaterialTheme.colors.onSurface
        )

        ColorItem(color = currentColor) {
            showPickColorDialog = true
        }
    }
}