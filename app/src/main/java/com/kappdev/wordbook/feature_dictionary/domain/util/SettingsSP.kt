package com.kappdev.wordbook.feature_dictionary.domain.util

sealed class SettingsSP (val key: String) {
    object Name: SettingsSP(key = "settings")
    object Theme: SettingsSP(key = "theme")
    object SpeechLanguage: SettingsSP(key = "speech_language")
    object AppLanguage: SettingsSP(key = "app_language")
    object SpeechSpeedRate: SettingsSP(key = "speech_speed_rate")
    object DarkThemePrimaryColor: SettingsSP(key = "dark_theme_primary_color")
    object LightThemePrimaryColor: SettingsSP(key = "light_theme_primary_color")
}
