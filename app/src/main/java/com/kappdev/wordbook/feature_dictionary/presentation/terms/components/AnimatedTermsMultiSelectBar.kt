package com.kappdev.wordbook.feature_dictionary.presentation.terms.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.MoveToInbox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.ConfirmDialog
import com.kappdev.wordbook.core.presentation.components.CustomTopBar
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermViewModel
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermsTopBar

@Composable
fun AnimatedTermsMultiSelectBar(
    isVisible: Boolean,
    viewModel: TermViewModel
) {
    val items = viewModel.selectedList.size
    var showRemoveDialog by remember { mutableStateOf(false) }
    if (showRemoveDialog) {
        ConfirmDialog(
            title = stringResource(id = R.string.dialog_title_remove) + " $items " + stringResource(id = R.string.dialog_title_terms),
            message = stringResource(id = R.string.dialog_msg_remove_terms_conf),
            confirmText = stringResource(id = R.string.dialog_btn_remove),
            closeDialog = { showRemoveDialog = false },
            onConfirm = {
                viewModel.removeMultipleTerms()
                showRemoveDialog = false
            }
        )
    }

    var showMoveToDialog by remember { mutableStateOf(false) }
    if (showMoveToDialog) {
        MoveTermToSetDialog(
            multipleMove = true,
            viewModel = viewModel,
            closeDialog = { showMoveToDialog = false },
        )
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically { size -> -size },
        exit = fadeOut() + slideOutVertically { size -> -size }
    ) {
        CustomTopBar() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = stringResource(id = R.string.label_selected_items),
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = viewModel.selectedList.size.toString(),
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 18.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { showMoveToDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoveToInbox,
                            contentDescription = "move_all_icon",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }

                    IconButton(
                        onClick = { showRemoveDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteForever,
                            contentDescription = "remove_all_icon",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.setTopBar(TermsTopBar.Default)
                            viewModel.clearSelected()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "cancel_icon",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
        }
    }
}