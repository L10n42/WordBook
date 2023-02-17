package com.kappdev.wordbook.feature_dictionary.presentation.tests.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.ui.theme.Green200
import com.kappdev.wordbook.ui.theme.Green500
import com.kappdev.wordbook.ui.theme.Red200
import com.kappdev.wordbook.ui.theme.Red500

enum class AnswerFieldState {
    WRONG, CORRECT, DEFAULT
}

@Composable
fun AnswerField(
    value: String,
    answerFieldState: AnswerFieldState,
    onClick: () -> Unit = { }
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val lightTheme = MaterialTheme.colors.isLight
    val textColor = MaterialTheme.colors.onSurface
    val color: Color
    var trailingIcon: ImageVector? = null

    when (answerFieldState) {
        AnswerFieldState.DEFAULT -> {
            color = MaterialTheme.colors.onBackground
            trailingIcon = null
        }
        AnswerFieldState.CORRECT -> {
            color = if (lightTheme) Green500 else Green200
            trailingIcon = Icons.Default.Done
        }
        AnswerFieldState.WRONG -> {
            color = if (lightTheme) Red500 else Red200
            trailingIcon = Icons.Default.Close
        }
    }

    val icon: @Composable () -> Unit = {
        trailingIcon?.let {
            Icon(
                imageVector = trailingIcon,
                contentDescription = "trailing icon for correct/wrong answer",
                tint = color
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(color = Color.Transparent, shape = RoundedCornerShape(4.dp))
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            singleLine = true,
            readOnly = true,
            shape = RoundedCornerShape(4.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = textColor,
                focusedBorderColor = color,
                unfocusedBorderColor = color,
                backgroundColor = MaterialTheme.colors.surface
            ),
            trailingIcon = if (trailingIcon == null) null else icon,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { state ->
                    if (state.isFocused) {
                        onClick()
                        focusManager.clearFocus()
                    }
                }
        )
    }
}