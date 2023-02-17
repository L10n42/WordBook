package com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.core.presentation.components.SpeakIconWithPopupTranscription
import com.kappdev.wordbook.feature_dictionary.domain.model.CardTerm

@Composable
fun FrontCard(
    modifier: Modifier = Modifier,
    item: CardTerm,
    say: (String) -> Unit
) {
    val text = item.term.term
    val transcription = item.term.transcription

    Box(
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 7,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )

        SpeakIconWithPopupTranscription(
            modifier = Modifier.align(Alignment.TopEnd),
            text = transcription,
            onIconClick = { say(text) }
        )
    }
}