package com.kappdev.wordbook.feature_dictionary.presentation.sets.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.CustomTopBar

@Composable
fun SetsTopBar(
    onSearch: () -> Unit,
    onSort: () -> Unit,
    onSettings: () -> Unit
) {
    CustomTopBar(
        title = stringResource(R.string.title_sets),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            IconButton(onClick = onSearch) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search_icon",
                    tint = MaterialTheme.colors.onSurface
                )
            }
            IconButton(onClick = onSort) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = "sort_icon",
                    tint = MaterialTheme.colors.onSurface
                )
            }
            IconButton(onClick = onSettings) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "settings_icon",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}