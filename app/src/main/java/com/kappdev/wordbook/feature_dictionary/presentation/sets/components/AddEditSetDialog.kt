package com.kappdev.wordbook.feature_dictionary.presentation.sets.components

import android.view.Gravity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.ButtonsCouple
import com.kappdev.wordbook.core.presentation.components.DialogWithGravity
import com.kappdev.wordbook.core.presentation.components.MTextField

@ExperimentalComposeUiApi
@Composable
fun AddEditSetDialog(
    title: String,
    initName: String = "",
    initDescription: String = "",
    closeDialog: () -> Unit,
    onCancel: () -> Unit = closeDialog,
    onDone: (name: String, description: String) -> Unit
) {
    var name by remember { mutableStateOf(initName) }
    var description by remember { mutableStateOf(initDescription) }
    var nameError by remember { mutableStateOf(false) }

    DialogWithGravity(
        gravity = Gravity.BOTTOM,
        onDismissRequest = closeDialog,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                MTextField(
                    text = name,
                    isError = nameError,
                    hint = stringResource(R.string.set_name_label),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) { newText -> name = newText }

                Spacer(modifier = Modifier.height(16.dp))

                MTextField(
                    text = description,
                    hint = stringResource(R.string.set_des_label),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) { newText -> description = newText }

                ButtonsCouple(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    positiveTitleResId = R.string.btn_done_title,
                    negativeTitleResId = R.string.btn_cancel_title,
                    onNegativeClick = onCancel,
                    onPositiveClick = {
                        if (name.isNotBlank()) onDone(name, description) else nameError = true
                    }
                )
            }
        }
    }
}