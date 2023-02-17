package com.kappdev.wordbook.feature_dictionary.presentation.terms.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.core.presentation.components.CustomTopBarWithBackBtn

@Composable
fun TermsTopBar(
    title: String,
    onBackPress: () -> Unit,
    onSearch: () -> Unit,
    onSort: () -> Unit
) {
    CustomTopBarWithBackBtn(
        title = title,
        onBackPress = onBackPress
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
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
        }
    }
}