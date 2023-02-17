package com.kappdev.wordbook.core.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize

@Composable
fun <T> OutlineDropDown(
    modifier: Modifier = Modifier,
    itemsList: List<T>,
    selectedValue: String,
    label: String? = null,
    color: Color = MaterialTheme.colors.onSurface,
    textColor: Color = color,
    textSize: TextUnit = 16.sp,
    shape: Shape = RoundedCornerShape(4.dp),
    onItemSelected: (item: T) -> Unit,
    menuItem: @Composable (item: T) -> Unit
) {
    val context = LocalContext.current
    val orientation = context.resources.configuration.orientation

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var expanded by remember { mutableStateOf(false) }
    var boxSize by remember { mutableStateOf(Size.Zero) }

    val boxWidth = with(LocalDensity.current) { boxSize.width.toDp() }
    val boxHeight = with(LocalDensity.current) { boxSize.height.toDp() }
    val popupMaxHeight =
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) (boxHeight * 2) else (boxHeight * 6)

    val shrink = {
        expanded = false
        focusManager.clearFocus()
    }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            singleLine = true,
            readOnly = true,
            shape = shape,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = textColor,
                focusedLabelColor = MaterialTheme.colors.primary,
                unfocusedLabelColor = color,
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = color
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "dropdown menu icon",
                    tint = if (expanded) MaterialTheme.colors.primary else color,
                    modifier = Modifier.rotate(if (expanded) 180f else 360f)
                )
            },
            label = { label?.let { Text(text = label) } },
            textStyle = TextStyle(fontSize = textSize),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .focusRequester(focusRequester)
                .onFocusChanged { state -> expanded = state.isFocused }
                .onGloballyPositioned { coordinates -> boxSize = coordinates.size.toSize() }
        )

        DropdownMenu(
            expanded = expanded,
            modifier = Modifier
                .width(boxWidth)
                .heightIn(1.dp, popupMaxHeight),
            onDismissRequest = shrink,
        ) {
            itemsList.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onItemSelected(item)
                        shrink()
                    }
                ) {
                    menuItem(item)
                }
            }
        }
    }
}