package com.kappdev.wordbook.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonsCouple(
    modifier: Modifier,
    positiveTitleResId: Int,
    negativeTitleResId: Int,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary
            ),
            border = BorderStroke(1.dp, color = MaterialTheme.colors.primary),
            onClick = onNegativeClick
        ) {
            Text(
                text = stringResource(negativeTitleResId),
                fontSize = 16.sp
            )
        }

        Button(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(),
            border = null,
            onClick = onPositiveClick
        ) {
            Text(
                text = stringResource(positiveTitleResId),
                fontSize = 16.sp
            )
        }
    }
}