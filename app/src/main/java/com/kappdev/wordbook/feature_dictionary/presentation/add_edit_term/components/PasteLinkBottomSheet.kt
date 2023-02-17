package com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.ButtonsCouple
import com.kappdev.wordbook.core.presentation.components.MTextField

@Composable
fun PasteLinkBottomSheet(
    hideSheet: () -> Unit,
    onApply: (String) -> Unit
) {
    val init = ""
    var link by remember { mutableStateOf(init) }
    var isError by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ).padding(bottom = 32.dp, top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.download_photo_title),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface
        )

        MTextField(
            text = link,
            isError = isError,
            singleLine = true,
            hint = stringResource(R.string.label_paste_link_here),
            modifier = Modifier.fillMaxWidth(),
            onTextChanged = { newLink ->
                link = newLink
            }
        )

        ButtonsCouple(
            modifier = Modifier.fillMaxWidth(),
            positiveTitleResId = R.string.btn_download,
            negativeTitleResId = R.string.btn_cancel_title,
            onNegativeClick = hideSheet,
            onPositiveClick = {
                if (link.trim().isEmpty()) {
                    isError = true
                } else {
                    onApply(link)
                    hideSheet()
                }
            }
        )
    }
}