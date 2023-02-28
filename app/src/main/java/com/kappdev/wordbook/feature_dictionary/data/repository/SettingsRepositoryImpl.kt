package com.kappdev.wordbook.feature_dictionary.data.repository

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import com.kappdev.wordbook.core.domain.util.AppLanguage
import com.kappdev.wordbook.core.domain.util.term_to_speech.SpeechLanguage
import com.kappdev.wordbook.feature_dictionary.domain.repository.SettingsRepository
import com.kappdev.wordbook.feature_dictionary.domain.util.SettingsSP
import com.kappdev.wordbook.ui.theme.Blue200
import com.kappdev.wordbook.ui.theme.Blue500

class SettingsRepositoryImpl(context: Context) : SettingsRepository {

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
        editor.putString(SettingsSP.SpeechLanguage.key, speechLanguage.id).apply()
    }

    override fun getSpeechSpeed(): Float {
        return sharedPreferences.getFloat(SettingsSP.SpeechSpeedRate.key, 1.0f)
    }

    override fun setSpeechSpeed(speed: Float) {
        editor.putFloat(SettingsSP.SpeechSpeedRate.key, speed).apply()
    }

    override fun setTheme(theme: Boolean) {
        editor.putBoolean(SettingsSP.Theme.key, theme).apply()
    }

    override fun getTheme(): Boolean {
        return sharedPreferences.getBoolean(SettingsSP.Theme.key, true)
    }

    override fun setDarkThemePrimaryColor(color: Color) {
        editor.putString(SettingsSP.DarkThemePrimaryColor.key, color.toJson()).apply()
    }

    override fun getDarkThemePrimaryColor(): Color {
        val colorJson = sharedPreferences.getString(SettingsSP.DarkThemePrimaryColor.key, null)
        return if (colorJson != null) colorFromJson(colorJson) else Blue200
    }

    override fun setLightThemePrimaryColor(color: Color) {
        editor.putString(SettingsSP.LightThemePrimaryColor.key, color.toJson()).apply()
    }

    override fun getLightThemePrimaryColor(): Color {
        val colorJson = sharedPreferences.getString(SettingsSP.LightThemePrimaryColor.key, null)
        return if (colorJson != null) colorFromJson(colorJson) else Blue500
    }

    override fun setAppLanguage(language: String) {
        editor.putString(SettingsSP.AppLanguage.key, language).apply()
    }

    override fun getAppLanguage(): String {
        val default = AppLanguage.English.languageKey
        return sharedPreferences.getString(SettingsSP.AppLanguage.key, default) ?: default
    }

    private data class JsonColor(val red: Float, val green: Float, val blue: Float, val alpha: Float)


    private fun Color.toJson(): String {
        val jsonColorModel = JsonColor(red = this.red, green = this.green, blue = this.blue, alpha = this.alpha)
        return Gson().toJson(jsonColorModel)
    }

    private fun colorFromJson(json: String): Color {
        val jsonColorModel = Gson().fromJson(json, JsonColor::class.java)
        return Color(jsonColorModel.red, jsonColorModel.green, jsonColorModel.blue, jsonColorModel.alpha
        )
    }
}


