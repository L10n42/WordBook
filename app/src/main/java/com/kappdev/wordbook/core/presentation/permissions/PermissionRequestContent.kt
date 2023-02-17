package com.kappdev.wordbook.core.presentation.permissions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R

@Composable
fun PermissionRequestContent(
    message: String,
    showButton: Boolean = true,
    btnText: String = stringResource(id = R.string.btn_request_permissions),
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = message,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.Center)
                .padding(bottom = 32.dp)
        )

        if (showButton){
            Button(
                shape = RoundedCornerShape(16.dp),
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 42.dp)
            ) {
                Text(
                    text = btnText,
                    fontSize = 18.sp
                )
            }
        }
    }
}