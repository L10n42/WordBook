package com.kappdev.wordbook.core.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.kappdev.wordbook.R

@Composable
fun ColorPickerDialog(
    hideDialog: () -> Unit,
    onColorChoose: (color: Color) -> Unit
) {
    val controller = rememberColorPickerController()

    Dialog(onDismissRequest = hideDialog) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colors.surface
        ) {
            Column {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(all = 16.dp)
                ) {
                    HsvColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp)
                            .align(Alignment.Center),
                        controller = controller,
                        onColorChanged = {}
                    )
                    AlphaTile(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .align(Alignment.TopCenter)
                            .clip(RoundedCornerShape(8.dp)),
                        controller = controller
                    )
                    BrightnessSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .align(Alignment.BottomCenter),
                        controller = controller,
                    )
                }
                Buttons(onCancel = hideDialog) {
                    onColorChoose(controller.selectedColor.value)
                    hideDialog()
                }
            }
        }
    }
}

@Composable
private fun Buttons(
    onCancel: () -> Unit,
    onOk: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = onCancel) {
            Text(
                text = stringResource(R.string.dialog_btn_cancel),
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp,
            )
        }
        TextButton(onClick = onOk) {
            Text(
                text = stringResource(R.string.btn_ok),
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp,
            )
        }
    }
}