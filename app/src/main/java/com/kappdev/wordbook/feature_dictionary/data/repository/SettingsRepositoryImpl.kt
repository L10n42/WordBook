package com.kappdev.wordbook.feature_dictionary.data.repository

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.kappdev.wordbook.core.domain.util.AppLanguage
import com.kappdev.wordbook.core.domain.util.term_to_speech.SpeechLanguage
import com.kappdev.wordbook.feature_dictionary.domain.repository.SettingsRepository
import com.kappdev.wordbook.feature_dictionary.domain.util.SettingsSP
import com.kappdev.wordbook.ui.theme.Blue200
import com.kappdev.wordbook.ui.theme.Blue500

class SettingsRepositoryImpl(context: Context) : SettingsRepository {
    private companion object ColorConst {
        const val RED = "_red"
        const val GREEN = "_green"
        const val BLUE = "_blue"
        const val ALPHA = "_alpha"
    }

    private val sharedPreferences = context.getSharedPreferences(SettingsSP.Name.key, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getSpeechLanguage(): SpeechLanguage {
        val id = sharedPreferences.getString(SettingsSP.SpeechLanguage.key, SpeechLanguage.UK.id)
        when (id) {
            SpeechLanguage.US.id -> return SpeechLanguage.US
            SpeechLanguage.UK.id -> return SpeechLanguage.UK

            else -> return SpeechLanguage.UK
        }
    }

    override fun setSpeechLanguage(speechLanguage: SpeechLanguage) {
        editor.putString(SettingsSP.SpeechLanguage.key, speechLanguage.id)
        editor.apply()
    }

    override fun getSpeechSpeed(): Float {
        return sharedPreferences.getFloat(SettingsSP.SpeechSpeedRate.key, 1.0f)
    }

    override fun setSpeechSpeed(speed: Float) {
        editor.putFloat(SettingsSP.SpeechSpeedRate.key, speed)
        editor.apply()
    }

    override fun setTheme(theme: Boolean) {
        editor.putBoolean(SettingsSP.Theme.key, theme)
        editor.apply()
    }

    override fun getTheme(): Boolean {
        return sharedPreferences.getBoolean(SettingsSP.Theme.key, true)
    }

    override fun setDarkThemePrimaryColor(color: Color) {
        editor.putFloat(SettingsSP.DarkThemePrimaryColor.key + RED, color.red)
        editor.putFloat(SettingsSP.DarkThemePrimaryColor.key + GREEN, color.green)
        editor.putFloat(SettingsSP.DarkThemePrimaryColor.key + BLUE, color.blue)
        editor.putFloat(SettingsSP.DarkThemePrimaryColor.key + ALPHA, color.alpha)
        editor.apply()
    }

    override fun getDarkThemePrimaryColor(): Color {
        val r = sharedPreferences.getFloat(SettingsSP.DarkThemePrimaryColor.key + RED, Blue200.red)
        val g = sharedPreferences.getFloat(SettingsSP.DarkThemePrimaryColor.key + GREEN, Blue200.green)
        val b = sharedPreferences.getFloat(SettingsSP.DarkThemePrimaryColor.key + BLUE, Blue200.blue)
        val a = sharedPreferences.getFloat(SettingsSP.DarkThemePrimaryColor.key + ALPHA, Blue200.alpha)
        return Color(r, g, b, a)
    }

    override fun setLightThemePrimaryColor(color: Color) {
        editor.putFloat(SettingsSP.LightThemePrimaryColor.key + RED, color.red)
        editor.putFloat(SettingsSP.LightThemePrimaryColor.key + GREEN, color.green)
        editor.putFloat(SettingsSP.LightThemePrimaryColor.key + BLUE, color.blue)
        editor.putFloat(SettingsSP.LightThemePrimaryColor.key + ALPHA, color.alpha)
        editor.apply()
    }

    override fun getLightThemePrimaryColor(): Color {
        val r = sharedPreferences.getFloat(SettingsSP.LightThemePrimaryColor.key + RED, Blue500.red)
        val g = sharedPreferences.getFloat(SettingsSP.LightThemePrimaryColor.key + GREEN, Blue500.green)
        val b = sharedPreferences.getFloat(SettingsSP.LightThemePrimaryColor.key + BLUE, Blue500.blue)
        val a = sharedPreferences.getFloat(SettingsSP.LightThemePrimaryColor.key + ALPHA, Blue500.alpha)
        return Color(r, g, b, a)
    }

    override fun setAppLanguage(language: String) {
        editor.putString(SettingsSP.AppLanguage.key, language).apply()
    }

    override fun getAppLanguage(): String {
        val default = AppLanguage.English.languageKey
        return sharedPreferences.getString(SettingsSP.AppLanguage.key, default) ?: default
    }
}