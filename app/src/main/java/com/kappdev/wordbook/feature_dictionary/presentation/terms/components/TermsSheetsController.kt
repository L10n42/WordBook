package com.kappdev.wordbook.feature_dictionary.presentation.terms.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kappdev.wordbook.core.presentation.components.MoreBottomSheet
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.feature_dictionary.domain.util.DrawerItems
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermViewModel
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermsBottomSheet
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermsDialog

@Composable
fun TermsSheetsController(
    viewModel: TermViewModel,
    navController: NavHostController,
    currentSheet: TermsBottomSheet,
    onCloseBS: () -> Unit
) {

    when(currentSheet) {
        is TermsBottomSheet.Order -> TermOrderSection(viewModel)
        is TermsBottomSheet.More -> {
            MoreBottomSheet(
                title = currentSheet.term.term,
                items = DrawerItems.popupTermMoreItems,
                onClick = { id ->
                    onCloseBS()
                    when(id) {
                        DrawerItems.MoveTo.id -> viewModel.showDialog(TermsDialog.MoveTo(currentSheet.term))
                        DrawerItems.Remove.id -> viewModel.showDialog(TermsDialog.Remove(currentSheet.term))
                        DrawerItems.Edit.id -> {
                            navController.navigate(
                                Screen.AddEditTerm.route.plus(
                                    "?setId=${currentSheet.term.setId}&termId=${currentSheet.term.termId}"
                                )
                            )
                        }
                    }
                }
            )
        }
    }
}