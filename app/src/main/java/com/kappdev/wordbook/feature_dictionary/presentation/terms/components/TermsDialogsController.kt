package com.kappdev.wordbook.feature_dictionary.presentation.terms.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.ConfirmDialog
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermViewModel
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermsDialog

@Composable
fun TermsDialogsController(
    currentDialog: TermsDialog,
    viewModel: TermViewModel
) {
    val closeDialog: () -> Unit = {
        viewModel.closeDialog()
    }
    when(currentDialog) {
        is TermsDialog.MoveTo -> {
            MoveTermToSetDialog(
                term = currentDialog.term,
                viewModel = viewModel,
                closeDialog = closeDialog
            )
        }
        is TermsDialog.Remove -> {
            ConfirmDialog(
                title = stringResource(id = R.string.dialog_title_remove_term),
                message = stringResource(id = R.string.dialog_msg_remove_term_conf) + " \"${currentDialog.term.term}\" ?",
                confirmText = stringResource(id = R.string.dialog_btn_remove),
                closeDialog = closeDialog,
                onConfirm = {
                    viewModel.removeSingleTerm(currentDialog.term)
                    closeDialog()
                }
            )
        }
    }
}