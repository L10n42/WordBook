package com.kappdev.wordbook.feature_dictionary.presentation.sets.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.MoreBottomSheet
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.feature_dictionary.domain.util.DrawerItems.Edit
import com.kappdev.wordbook.feature_dictionary.domain.util.DrawerItems.FlashCards
import com.kappdev.wordbook.feature_dictionary.domain.util.DrawerItems.Remove
import com.kappdev.wordbook.feature_dictionary.domain.util.DrawerItems.ShareSet
import com.kappdev.wordbook.feature_dictionary.domain.util.DrawerItems.Tests
import com.kappdev.wordbook.feature_dictionary.domain.util.DrawerItems.Writing
import com.kappdev.wordbook.feature_dictionary.domain.util.DrawerItems.popupSetMoreItems
import com.kappdev.wordbook.feature_dictionary.domain.util.SnackbarType
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsBottomSheet
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsDialog
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsViewModel

@Composable
fun SetSheetsController(
    viewModel: SetsViewModel,
    navController: NavHostController,
    currentScreen: SetsBottomSheet,
    onCloseBS: () -> Unit
) {
    when(currentScreen) {
        is SetsBottomSheet.Order -> {
            SetsOrderSheet(viewModel)
        }
        is SetsBottomSheet.More -> {
            fun putSetIdAndNavigate(route: String) {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "setsIds",
                    value = listOf(currentScreen.set.setId)
                )
                navController.navigate(route)
            }

            val testsWarningMsg = stringResource(R.string.warning_incorrect_size_of_set_for_tests_mode)
            val writingWarningMsg = stringResource(R.string.warning_incorrect_size_of_set_for_writing_mode)
            val flashcardsWarningMsg = stringResource(R.string.warning_incorrect_size_of_set_for_flashcards_mode)

            MoreBottomSheet(
                title = currentScreen.set.name,
                items = popupSetMoreItems,
                onClick = { id ->
                    onCloseBS()
                    when(id) {
                        Edit.id -> viewModel.showDialog(SetsDialog.Edit(currentScreen.set))
                        Remove.id -> viewModel.showDialog(SetsDialog.Remove(currentScreen.set))
                        ShareSet.id -> viewModel.exportSet(currentScreen.set.setId)
                        Writing.id -> {
                            if (currentScreen.set.number > 0) {
                                putSetIdAndNavigate(Screen.Writing.route)
                            } else {
                                viewModel.makeSnackbar(writingWarningMsg, SnackbarType.Warning)
                            }
                        }
                        Tests.id -> {
                            if (currentScreen.set.number > 4) {
                                putSetIdAndNavigate(Screen.Tests.route)
                            } else {
                                viewModel.makeSnackbar(testsWarningMsg, SnackbarType.Warning)
                            }
                        }
                        FlashCards.id -> {
                            if (currentScreen.set.number > 1) {
                                putSetIdAndNavigate(Screen.FlashCards.route)
                            } else {
                                viewModel.makeSnackbar(flashcardsWarningMsg, SnackbarType.Warning)
                            }
                        }
                    }
                }
            )
        }
    }
}