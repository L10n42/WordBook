package com.kappdev.wordbook.core.presentation.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedSearchBar(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    placeHolder: String = "Search...",
    onSearch: (String) -> Unit,
    onCancelBtnClick: () -> Unit,
) {
    var text by rememberSaveable { mutableStateOf("") }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically { size -> -size },
        exit = fadeOut() + slideOutVertically { size -> -size }
    ) {
        CustomTopBar() {
            TextField(
                value = text,
                singleLine = true,
                shape = RectangleShape,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search_icon",
                        tint = MaterialTheme.colors.onSurface
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            text = ""
                            onSearch(text)
                            onCancelBtnClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close_icon",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = placeHolder,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 18.sp
                    )
                },
                onValueChange = { newText ->
                    text = newText
                    onSearch(newText)
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    cursorColor = MaterialTheme.colors.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .then(modifier)
            )
        }
    }
}