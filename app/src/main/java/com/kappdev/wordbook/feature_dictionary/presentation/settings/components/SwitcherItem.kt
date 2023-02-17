package com.kappdev.wordbook.feature_dictionary.presentation.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R

@Composable
fun SwitcherItem(
    titleRes: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = titleRes),
            fontSize = 18.sp,
            color = MaterialTheme.colors.onSurface
        )

        Switch(
            checked = isChecked,
            onCheckedChange = { newVal ->
                onCheckedChange(newVal)
            },
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = MaterialTheme.colors.background,
                checkedThumbColor = MaterialTheme.colors.primary,
                checkedTrackColor = MaterialTheme.colors.primary,
                checkedTrackAlpha = 0.3f
            )
        )
    }
}