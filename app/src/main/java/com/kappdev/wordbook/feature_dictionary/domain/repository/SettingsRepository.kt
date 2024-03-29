package com.kappdev.wordbook.feature_dictionary.domain.repository

import androidx.compose.ui.graphics.Color
import com.kappdev.wordbook.core.domain.util.term_to_speech.SpeechLanguage
import com.kappdev.wordbook.feature_dictionary.domain.util.SetOrder
import com.kappdev.wordbook.feature_dictionary.domain.util.TermOrder

interface SettingsRepository {

    fun getSpeechLanguage(): SpeechLanguage

    fun setSpeechLanguage(speechLanguage: SpeechLanguage)

    fun getSpeechSpeed(): Float

    fun setSpeechSpeed(speed: Float)

    fun setTheme(theme: Boolean)

    fun getTheme(): Boolean

    fun setDarkThemePrimaryColor(color: Color)

    fun getDarkThemePrimaryColor(): Color

    fun setLightThemePrimaryColor(color: Color)

    fun getLightThemePrimaryColor(): Color

    fun setAppLanguage(language: String)

    fun getAppLanguage(): String

    fun setSetsOrder(order: SetOrder)

    fun getSetsOrder(): SetOrder

    fun setTermsOrder(order: TermOrder)

    fun getTermsOrder(): TermOrder
}