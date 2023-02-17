package com.kappdev.wordbook.core.domain.util.term_to_speech

import android.content.Context
import android.speech.tts.TextToSpeech
import com.kappdev.wordbook.feature_dictionary.domain.repository.SettingsRepository

class TermToSpeech(
    private val context: Context,
    private val settings: SettingsRepository
) {
    private var textToSpeech: TextToSpeech
    private var result: Int = -1

    init {
        textToSpeech = TextToSpeech(context) { mResult ->
            result = mResult
        }
    }

    fun say(text: String) {
        if (result == TextToSpeech.SUCCESS) {
            val language = settings.getSpeechLanguage().language
            val speed = settings.getSpeechSpeed()

            textToSpeech.let { tts ->
                if (tts.isSpeaking) tts.stop()

                tts.language = language
                tts.setSpeechRate(speed)
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    companion object Languages {
        val list = listOf(
            SpeechLanguage.UK,
            SpeechLanguage.US
        )
    }
}