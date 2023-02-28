package com.kappdev.wordbook.core.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.feature_dictionary.domain.util.SnackbarType

@Composable
fun CustomSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(DEFAULT_HORIZONTAL_PADDING, DEFAULT_VERTICAL_PADDING),
    type: SnackbarType,
    onActionClick: () -> Unit = {}
) {
    val isThemeLight = MaterialTheme.colors.isLight
    val contentColor = Color.White
    var backgroundColor: Color = MaterialTheme.colors.surface
    var icon: ImageVector? = null
    when (type) {
        SnackbarType.Error -> {
            backgroundColor = if (isThemeLight) Red400 else Red300
            icon = Icons.Default.Warning
        }
        SnackbarType.Success -> {
            backgroundColor = if (isThemeLight) Green400 else Green300
            icon = Icons.Default.Verified
        }
        SnackbarType.Info -> {
            backgroundColor = if (isThemeLight) Blue400 else Blue300
            icon = Icons.Default.Info
        }
        SnackbarType.Warning -> {
            backgroundColor = if (isThemeLight) Amber400 else Amber300
            icon = Icons.Default.Error
        }
        SnackbarType.Default -> {
            backgroundColor = MaterialTheme.colors.surface
            icon = null
        }
    }

    CustomSnackbarContent(
        snackbarHostState = snackbarHostState,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        modifier = modifier,
        paddingValues = paddingValues,
        icon = icon,
        onActionClick = onActionClick
    )
}

@Composable
private fun CustomSnackbarContent(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    icon: ImageVector?,
    contentColor: Color,
    backgroundColor: Color,
    onActionClick: () -> Unit = {}
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier
    ) { data ->
        Snackbar(
            modifier = Modifier.padding(paddingValues),
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            action = {
                data.actionLabel?.let { label ->
                    TextButton(onClick = onActionClick) {
                        Text(
                            text = label,
                            color = contentColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                icon?.let {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                }
                Text(
                    text = data.message,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private val DEFAULT_HORIZONTAL_PADDING = 8.dp
private val DEFAULT_VERTICAL_PADDING = 16.dp

// snackbar colors
private val Red300 = Color(0xFFE57373)
private val Red400 = Color(0xFFEF5350)
private val Amber300 = Color(0xFFFFD54F)
private val Amber400 = Color(0xFFFFCA28)
private val Blue300 = Color(0xFF64B5F6)
private val Blue400 = Color(0xFF42A5F5)
private val Green300 = Color(0xFF81C784)
private val Green400 = Color(0xFF66BB6A)