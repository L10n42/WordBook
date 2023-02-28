package com.kappdev.wordbook.feature_dictionary.data.repository

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import com.kappdev.wordbook.core.domain.util.AppLanguage
import com.kappdev.wordbook.core.domain.util.term_to_speech.SpeechLanguage
import com.kappdev.wordbook.feature_dictionary.domain.repository.SettingsRepository
import com.kappdev.wordbook.feature_dictionary.domain.util.OrderType
import com.kappdev.wordbook.feature_dictionary.domain.util.SetOrder
import com.kappdev.wordbook.feature_dictionary.domain.util.TermOrder
import com.kappdev.wordbook.ui.theme.Blue200
import com.kappdev.wordbook.ui.theme.Blue500

class SettingsRepositoryImpl(context: Context) : SettingsRepository {

    private val sharedPreferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getSpeechLanguage(): SpeechLanguage {
        val id = sharedPreferences.getString(SPEECH_LANGUAGE_KEY, SpeechLanguage.UK.id)
        when (id) {
            SpeechLanguage.US.id -> return SpeechLanguage.US
            SpeechLanguage.UK.id -> return SpeechLanguage.UK

            else -> return SpeechLanguage.UK
        }
    }

    override fun setSpeechLanguage(speechLanguage: SpeechLanguage) {
        editor.putString(SPEECH_LANGUAGE_KEY, speechLanguage.id).apply()
    }

    override fun getSpeechSpeed(): Float {
        return sharedPreferences.getFloat(SPEECH_SPEED_RATE_KEY, 1.0f)
    }

    override fun setSpeechSpeed(speed: Float) {
        editor.putFloat(SPEECH_SPEED_RATE_KEY, speed).apply()
    }

    override fun setTheme(theme: Boolean) {
        editor.putBoolean(THEME_KEY, theme).apply()
    }

    override fun getTheme(): Boolean {
        return sharedPreferences.getBoolean(THEME_KEY, true)
    }

    override fun setDarkThemePrimaryColor(color: Color) {
        editor.putString(DARK_THEME_PRIMARY_COLOR_KEY, color.toJson()).apply()
    }

    override fun getDarkThemePrimaryColor(): Color {
        val colorJson = sharedPreferences.getString(DARK_THEME_PRIMARY_COLOR_KEY, null)
        return if (colorJson != null) colorFromJson(colorJson) else Blue200
    }

    override fun setLightThemePrimaryColor(color: Color) {
        editor.putString(LIGHT_THEME_PRIMARY_COLOR_KEY, color.toJson()).apply()
    }

    override fun getLightThemePrimaryColor(): Color {
        val colorJson = sharedPreferences.getString(LIGHT_THEME_PRIMARY_COLOR_KEY, null)
        return if (colorJson != null) colorFromJson(colorJson) else Blue500
    }

    override fun setAppLanguage(language: String) {
        editor.putString(APP_LANGUAGE_KEY, language).apply()
    }

    override fun getAppLanguage(): String {
        val default = AppLanguage.English.languageKey
        return sharedPreferences.getString(APP_LANGUAGE_KEY, default) ?: default
    }

    override fun setSetsOrder(order: SetOrder) {
        val jsonOrder = JsonOrder(order = order.id, type = order.orderType.id)
        editor.putString(SETS_ORDER_KEY, Gson().toJson(jsonOrder)).apply()
    }

    override fun getSetsOrder(): SetOrder {
        val json = sharedPreferences.getString(SETS_ORDER_KEY, null)?: return DefaultSetsOrder
        val jsonOrder = Gson().fromJson(json, JsonOrder::class.java)
        val type = OrderType.getById(jsonOrder.type)
        return SetOrder.getById(jsonOrder.order, type)?: DefaultSetsOrder
    }

    override fun setTermsOrder(order: TermOrder) {
        val jsonOrder = JsonOrder(order = order.id, type = order.orderType.id)
        editor.putString(TERMS_ORDER_KEY, Gson().toJson(jsonOrder)).apply()
    }

    override fun getTermsOrder(): TermOrder {
        val json = sharedPreferences.getString(TERMS_ORDER_KEY, null)?: return DefaultTermsOrder
        val jsonOrder = Gson().fromJson(json, JsonOrder::class.java)
        val type = OrderType.getById(jsonOrder.type)
        return TermOrder.getById(jsonOrder.order, type)?: DefaultTermsOrder
    }

    private data class JsonOrder(val order: String, val type: String)
    private data class JsonColor(val red: Float, val green: Float, val blue: Float, val alpha: Float)

    private fun Color.toJson(): String {
        val jsonColorModel = JsonColor(red = this.red, green = this.green, blue = this.blue, alpha = this.alpha)
        return Gson().toJson(jsonColorModel)
    }

    private fun colorFromJson(json: String): Color {
        val jsonColorModel = Gson().fromJson(json, JsonColor::class.java)
        return Color(jsonColorModel.red, jsonColorModel.green, jsonColorModel.blue, jsonColorModel.alpha)
    }

    companion object {
        private val DefaultSetsOrder = SetOrder.Name(OrderType.Ascending)
        private val DefaultTermsOrder = TermOrder.Term(OrderType.Ascending)

        const val SETTINGS = "settings"
        const val THEME_KEY = "is_theme_dark"
        const val SETS_ORDER_KEY = "sets_order"
        const val TERMS_ORDER_KEY = "terms_order"
        const val SPEECH_LANGUAGE_KEY = "speech_language"
        const val APP_LANGUAGE_KEY = "app_language"
        const val SPEECH_SPEED_RATE_KEY = "speech_speed_rate"
        const val DARK_THEME_PRIMARY_COLOR_KEY = "dark_theme_primary_color"
        const val LIGHT_THEME_PRIMARY_COLOR_KEY = "light_theme_primary_color"
    }
}


