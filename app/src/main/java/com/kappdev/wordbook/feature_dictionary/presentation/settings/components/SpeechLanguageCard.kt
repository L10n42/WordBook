package com.kappdev.wordbook.feature_dictionary.presentation.settings.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.domain.util.term_to_speech.TermToSpeech
import com.kappdev.wordbook.core.presentation.components.OutlineDropDown
import com.kappdev.wordbook.feature_dictionary.presentation.settings.SettingsViewModel

@ExperimentalMaterialApi
@Composable
fun SpeechLanguageCard(
    viewModel: SettingsViewModel
) {
    val languages = TermToSpeech.Languages.list
    val currentLanguage = viewModel.currentSpeechLanguage.value

    Box(
        modifier = Modifier.fillMaxWidth().padding(all = 16.dp)
    ) {
        OutlineDropDown(
            itemsList = languages,
            shape = RoundedCornerShape(8.dp),
            selectedValue = stringResource(currentLanguage.resId),
            label = stringResource(R.string.settings_title_speech_language),
            onItemSelected = { item ->
                viewModel.setSpeechLanguage(item)
            }
        ) { item ->
            Text(
                text = stringResource(item.resId),
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
