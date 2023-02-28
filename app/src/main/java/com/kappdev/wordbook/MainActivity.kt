package com.kappdev.wordbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kappdev.wordbook.core.domain.util.AppLanguage
import com.kappdev.wordbook.core.presentation.navigation.components.SetupNavGraph
import com.kappdev.wordbook.core.presentation.permissions.RequestMultiplePermissions
import com.kappdev.wordbook.feature_dictionary.data.repository.SettingsRepositoryImpl
import com.kappdev.wordbook.feature_dictionary.domain.util.SettingsSP
import com.kappdev.wordbook.ui.theme.Blue200
import com.kappdev.wordbook.ui.theme.Blue500
import com.kappdev.wordbook.ui.theme.WordBookTheme
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var navController: NavHostController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var settingsRepository: SettingsRepositoryImpl

    private val isThemeDark = mutableStateOf(true)
    private val appLanguage = mutableStateOf(AppLanguage.English.languageKey)
    private val darkThemePrimaryColor = mutableStateOf(Blue200)
    private val lightThemePrimaryColor = mutableStateOf(Blue500)

    @OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class,
        ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingsRepository = SettingsRepositoryImpl(this)
        sharedPreferences = getSharedPreferences(SettingsSP.Name.key, Context.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        readAll()

        setContent {
            SetLanguage(language = appLanguage.value)

            WordBookTheme(
                darkTheme = isThemeDark.value,
                darkThemePrimaryColor = darkThemePrimaryColor.value,
                lightThemePrimaryColor = lightThemePrimaryColor.value,
            ) {
                val systemUiController = rememberSystemUiController()
                val statusBarColor = MaterialTheme.colors.surface
                val navigationBarColor = MaterialTheme.colors.background
                SideEffect {
                    systemUiController.setNavigationBarColor(navigationBarColor)
                    systemUiController.setStatusBarColor(statusBarColor)
                }

                RequestMultiplePermissions(
                    permissions = listOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    navController = rememberNavController()
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }

    private fun readAll() {
        readTheme()
        readDarkThemePrimaryColor()
        readLightThemePrimaryColor()
        readAppLanguage()
    }

    private fun readAppLanguage() { appLanguage.value = settingsRepository.getAppLanguage() }
    private fun readTheme() { isThemeDark.value = settingsRepository.getTheme() }
    private fun readDarkThemePrimaryColor() { darkThemePrimaryColor.value = settingsRepository.getDarkThemePrimaryColor() }
    private fun readLightThemePrimaryColor() { lightThemePrimaryColor.value = settingsRepository.getLightThemePrimaryColor() }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key) {
            SettingsSP.Theme.key -> readTheme()
            SettingsSP.AppLanguage.key -> readAppLanguage()
            SettingsSP.DarkThemePrimaryColor.key -> readDarkThemePrimaryColor()
            SettingsSP.LightThemePrimaryColor.key -> readLightThemePrimaryColor()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}

@Composable
private fun SetLanguage(language: String) {
    val locale = Locale(language)
    val configuration = LocalConfiguration.current
    configuration.setLocale(locale)
    val resources = LocalContext.current.resources
    resources.updateConfiguration(configuration, resources.displayMetrics)
}