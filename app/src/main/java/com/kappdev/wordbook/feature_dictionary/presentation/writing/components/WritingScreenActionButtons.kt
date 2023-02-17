package com.kappdev.wordbook.feature_dictionary.presentation.writing.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.ui.theme.Amber500
import com.kappdev.wordbook.ui.theme.Green500

@Composable
fun WritingScreenActionButtons(
    modifier: Modifier = Modifier,
    showAnswer: () -> Unit,
    submit: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        Button(
            onClick = showAnswer,
            modifier = Modifier.width(ButtonsWidth),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Amber500,
                contentColor = White
            )
        ) {
            Text(
                text = stringResource(R.string.btn_show_answer),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = submit,
            modifier = Modifier.width(ButtonsWidth),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Green500,
                contentColor = White
            )
        ) {
            Text(
                text = stringResource(R.string.btn_submit),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun WritingScreenNextButton(
    modifier: Modifier = Modifier,
    color: Color,
    next: () -> Unit
) {
    Button(
        onClick = next,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            contentColor = White
        )
    ) {
        Text(
            text = stringResource(R.string.btn_next),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

private val ButtonsWidth = 150.dp