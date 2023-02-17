package com.kappdev.wordbook.feature_dictionary.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingItemWithDescription(
    titleRes: Int,
    descriptionRes: Int? = null,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = titleRes),
            fontSize = 18.sp,
            color = MaterialTheme.colors.onSurface
        )

        if (descriptionRes != null) {
            Text(
                text = stringResource(id = descriptionRes),
                fontSize = 16.sp,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}