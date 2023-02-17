package com.kappdev.wordbook.core.domain.util

import com.kappdev.wordbook.R

sealed class AppLanguage(val titleResId: Int, val languageKey: String) {
    object English: AppLanguage(R.string.language_english, "en")
    object Ukrainian: AppLanguage(R.string.language_ukrainian, "uk")
}
