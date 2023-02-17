package com.kappdev.wordbook.feature_dictionary.presentation.sets.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.ConfirmDialog
import com.kappdev.wordbook.feature_dictionary.data.util.IDGenerator
import com.kappdev.wordbook.feature_dictionary.domain.model.Set
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsDialog
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsEvent
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsViewModel

@ExperimentalComposeUiApi
@Composable
fun SetsDialogController(
    currentDialog: SetsDialog,
    viewModel: SetsViewModel
) {
    val closeDialog: () -> Unit = {
        viewModel.closeDialog()
    }
    when(currentDialog) {
        is SetsDialog.Add -> {
            AddEditSetDialog(
                title = stringResource(id = R.string.dialog_title_new_set),
                closeDialog = closeDialog,
                onDone = { name, description ->
                    val newSet = Set(
                        setId = IDGenerator().generateID(),
                        name = name,
                        description = description,
                        timestamp = System.currentTimeMillis()
                    )
                    viewModel.onEvent(SetsEvent.AddSet(newSet))
                    closeDialog()
                }
            )
        }
        is SetsDialog.Edit -> {
            AddEditSetDialog(
                title = stringResource(id = R.string.dialog_title_edit_set),
                initName = currentDialog.set.name,
                initDescription = currentDialog.set.description,
                closeDialog = closeDialog,
            ) { name, description ->
                val newSet = currentDialog.set.copy(
                    name = name,
                    description = description,
                    timestamp = System.currentTimeMillis()
                )
                viewModel.onEvent(SetsEvent.UpdateSet(newSet))
                closeDialog()
            }
        }
        is SetsDialog.Remove -> {
            ConfirmDialog(
                title = stringResource(id = R.string.dialog_title_remove_set),
                message = stringResource(id = R.string.dialog_msg_remove_conf) + " \"${currentDialog.set.name}\" ?",
                confirmText = stringResource(id = R.string.dialog_btn_remove),
                closeDialog = closeDialog,
                onConfirm = {
                    viewModel.onEvent(SetsEvent.RemoveSet(currentDialog.set))
                    closeDialog()
                }
            )
        }
    }
}