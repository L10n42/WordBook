package com.kappdev.wordbook.feature_dictionary.presentation.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.presentation.settings.SettingsViewModel

@Composable
fun SpeechSpeedCard(
    settingsViewModel: SettingsViewModel
) {
    val currentSpeed = settingsViewModel.getSpeechSpeed()
    val currentPosition = (currentSpeed / 2) * 100

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        var sliderPosition by remember {
            mutableStateOf(currentPosition)
        }
        var rate by remember {
            mutableStateOf(currentSpeed)
        }

        Text(
            text = stringResource(id = R.string.settings_title_speech_speed),
            fontSize = 18.sp,
            color = MaterialTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = rate.toString(),
            fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Slider(
            value = sliderPosition,
            onValueChange = { newPos ->
                sliderPosition = newPos
                rate = String.format("%.1f", (newPos * 0.01f) * 2f).toFloat()
            },
            valueRange = 0f..100f,
            onValueChangeFinished = {
                settingsViewModel.setSpeechSpeed(rate)
            },
            steps = 19,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.primary,
                activeTrackColor = MaterialTheme.colors.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}