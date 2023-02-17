package com.kappdev.wordbook.core.presentation.navigation

sealed class Screen(val route: String) {
    object Sets: Screen(route = "sets_screen")
    object Settings: Screen(route = "settings")
    object Terms: Screen(route = "terms_screen")
    object SplashScreen: Screen(route = "splash_screen")
    object FlashCards: Screen(route = "flash_cards_screen")
    object Tests: Screen(route = "tests_screen")
    object Writing: Screen(route = "writing_screen")
    object AddEditTerm: Screen(route = "add_edit_term_screen")
}
