package com.kappdev.wordbook.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.feature_dictionary.domain.model.DrawerItem
import com.kappdev.wordbook.feature_dictionary.domain.util.DrawerItems

@Composable
fun MoreBottomSheet(
    title: String,
    items: List<DrawerItem>,
    onClick: (id: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {
        Title(title)

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            itemsIndexed(items) { index, currentItem ->
                PopupItem(
                    item = currentItem,
                    onItemClick = onClick
                )
            }
        }
    }
}

@Composable
private fun PopupItem(
    item: DrawerItem,
    onItemClick: (String) -> Unit
) {
    DropdownMenuItem(
        onClick = { onItemClick(item.id) },
        modifier = Modifier.fillMaxWidth()
    ) {
        val iconColor = when(item.id) {
            DrawerItems.Remove.id -> MaterialTheme.colors.error
            else -> MaterialTheme.colors.onBackground
        }
        Icon(
            imageVector = item.icon,
            contentDescription = "popup item icon",
            tint = iconColor
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(id = item.titleResId),
            fontSize = 16.sp,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun Title(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth().padding(all = 16.dp)
        )
        Divider()
    }
}