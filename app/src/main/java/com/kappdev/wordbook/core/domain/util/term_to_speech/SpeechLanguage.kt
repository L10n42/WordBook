package com.kappdev.wordbook.core.domain.util.term_to_speech

import com.kappdev.wordbook.R
import java.util.*

sealed class SpeechLanguage(
    val language: Locale,
    val id: String,
    val resId: Int
    ) {
    object US: SpeechLanguage(language = Locale.US, id = "us", resId = R.string.speech_language_english_us)
    object UK: SpeechLanguage(language = Locale.UK, id = "uk", resId = R.string.speech_language_english_uk)
}
