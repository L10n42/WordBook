package com.kappdev.wordbook.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import com.kappdev.wordbook.R
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UnableToFind() {

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search_icon",
                modifier = Modifier.size(128.dp),
                tint = MaterialTheme.colors.onBackground
            )

            Text(
                text = stringResource(id = R.string.unable_to_find),
                fontSize = 18.sp,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}