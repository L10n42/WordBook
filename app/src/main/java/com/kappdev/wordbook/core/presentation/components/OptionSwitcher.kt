package com.kappdev.wordbook.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R

@Composable
fun OptionSwitcher(
    modifier: Modifier = Modifier,
    textId: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Text(
            text = stringResource(id = textId),
            fontSize = 18.sp,
            color = MaterialTheme.colors.onSurface
        )

        Switch(
            checked = isChecked,
            onCheckedChange = { newValue ->
                onCheckedChange(newValue)
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