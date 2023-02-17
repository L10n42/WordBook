package com.kappdev.wordbook.feature_dictionary.presentation.settings

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.wordbook.core.domain.util.AppLanguage
import com.kappdev.wordbook.core.domain.util.term_to_speech.SpeechLanguage
import com.kappdev.wordbook.feature_dictionary.domain.repository.SettingsRepository
import com.kappdev.wordbook.feature_dictionary.domain.use_cases.ImExDatabaseUseCases
import com.kappdev.wordbook.feature_dictionary.domain.util.SnackbarState
import com.kappdev.wordbook.feature_dictionary.domain.util.SnackbarType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val imExDatabaseUseCases: ImExDatabaseUseCases
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _snackbarState = mutableStateOf(SnackbarState())
    val snackbarState: State<SnackbarState> = _snackbarState

    private val _currentSpeechLanguage = mutableStateOf<SpeechLanguage>(SpeechLanguage.UK)
    val currentSpeechLanguage: State<SpeechLanguage> = _currentSpeechLanguage

    private val _currentAppLanguage = mutableStateOf<AppLanguage>(AppLanguage.English)
    val currentAppLanguage: State<AppLanguage> = _currentAppLanguage

    init {
        _currentAppLanguage.value = getAppLanguage()
    }
    init {
        _currentSpeechLanguage.value = settingsRepository.getSpeechLanguage()
    }

    fun setSnackbarVisibility(isVisible: Boolean) {
        _snackbarState.value = snackbarState.value.copy(isVisible = isVisible)
    }

    fun setAppLanguage(language: AppLanguage) {
        _currentAppLanguage.value = language
        settingsRepository.setAppLanguage(language.languageKey)
    }

    private fun getAppLanguage(): AppLanguage {
        return when(settingsRepository.getAppLanguage()) {
            AppLanguage.English.languageKey -> AppLanguage.English
            AppLanguage.Ukrainian.languageKey -> AppLanguage.Ukrainian
            else -> AppLanguage.English
        }
    }

    private fun startLoading() { _isLoading.value = true }
    private fun cancelLoading() { _isLoading.value = false }

    fun isThemeDark() = settingsRepository.getTheme()
    fun setTheme(theme: Boolean) = settingsRepository.setTheme(theme)

    fun setDarkThemePrimaryColor(color: Color) = settingsRepository.setDarkThemePrimaryColor(color)
    fun getDarkThemePrimaryColor() = settingsRepository.getDarkThemePrimaryColor()

    fun setLightThemePrimaryColor(color: Color) = settingsRepository.setLightThemePrimaryColor(color)
    fun getLightThemePrimaryColor() = settingsRepository.getLightThemePrimaryColor()

    fun setSpeechLanguage(language: SpeechLanguage) {
        _currentSpeechLanguage.value = language
        settingsRepository.setSpeechLanguage(language)
    }

    fun setSpeechSpeed(speed: Float) = settingsRepository.setSpeechSpeed(speed)
    fun getSpeechSpeed() = settingsRepository.getSpeechSpeed()

    fun importSet(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            startLoading()
            try {
                val success = imExDatabaseUseCases.importSet(uri)
                makeSnackbar(message = success, type = SnackbarType.Success)
            } catch (e: Exception) {
                makeSnackbar(message = e.message.toString(), type = SnackbarType.Error)
            } finally {
                cancelLoading()
            }
        }
    }

    fun exportDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            imExDatabaseUseCases.exportDatabase()
        }
    }

    fun importDatabase(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            startLoading()
            try {
                val success = imExDatabaseUseCases.importDatabase(uri)
                makeSnackbar(message = success, type = SnackbarType.Success)
            } catch (e: Exception) {
                makeSnackbar(message = e.message.toString(), type = SnackbarType.Error)
            } finally {
                cancelLoading()
            }
        }
    }

    private fun makeSnackbar(message: String, type: SnackbarType) {
        _snackbarState.value = SnackbarState(
            snackbarType = type,
            message = message,
            isVisible = true
        )
    }
}