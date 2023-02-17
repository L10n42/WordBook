package com.kappdev.wordbook.core.presentation.navigation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.components.AddEditTermScreen
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components.FlashCardsScreen
import com.kappdev.wordbook.feature_dictionary.presentation.sets.components.SetsScreen
import com.kappdev.wordbook.feature_dictionary.presentation.settings.components.SettingsScreen
import com.kappdev.wordbook.feature_dictionary.presentation.splash_screen.SplashScreen
import com.kappdev.wordbook.feature_dictionary.presentation.terms.components.TermsScreen
import com.kappdev.wordbook.feature_dictionary.presentation.tests.components.TestsScreen
import com.kappdev.wordbook.feature_dictionary.presentation.writing.components.WritingScreen

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {

        composable(
            route = Screen.Writing.route
        ) {
            WritingScreen(navController)
        }

        composable(
            route = Screen.FlashCards.route
        ) {
            FlashCardsScreen(navController)
        }

        composable(
            route = Screen.Tests.route
        ) {
           TestsScreen(navController)
        }

        composable(
            route = Screen.SplashScreen.route
        ) {
            SplashScreen(navController)
        }

        composable(
            route = Screen.Sets.route
        ) {
            SetsScreen(navController = navController)
        }

        composable(
            route = Screen.Settings.route
        ) {
            SettingsScreen(navController = navController)
        }

        composable(
            route = Screen.AddEditTerm.route + "?setId={setId}&termId={termId}",
            arguments = listOf(
                navArgument(
                    name = "setId"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(
                    name = "termId"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            AddEditTermScreen(navController = navController)
        }

        composable(
            route = Screen.Terms.route + "?setId={setId}",
            arguments = listOf(
                navArgument(
                    name = "setId"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            TermsScreen(navController = navController)
        }
    }
}