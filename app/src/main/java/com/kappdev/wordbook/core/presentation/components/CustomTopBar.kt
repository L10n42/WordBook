package com.kappdev.wordbook.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTopBarWithBackBtn(
    title: String? = null,
    color: Color = MaterialTheme.colors.surface,
    onBackPress: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    CustomTopBar(
        title = title,
        color = color,
        actions = actions,
        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back_icon",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    )
}

@Composable
fun CustomTopBar(
    color: Color = MaterialTheme.colors.surface,
    content: @Composable RowScope.() -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = color),
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            content = content,
            contentPadding = PaddingValues(all = 0.dp)
        )
        Divider()
    }
}

@Composable
fun CustomTopBar(
    title: String? = null,
    color: Color = MaterialTheme.colors.surface,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = color),
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            title = {
                title?.let {
                    Text(
                        text = title,
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1,
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                    )
                }
            },
            navigationIcon = navigationIcon,
            actions = actions
        )
        Divider()
    }
}