package com.kappdev.wordbook.feature_dictionary.presentation.sets.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.ConfirmDialog
import com.kappdev.wordbook.core.presentation.components.CustomTopBar
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.feature_dictionary.domain.util.DrawerItems
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsTopBar
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsViewModel

@Composable
fun AnimatedSetsMultiSelectBar(
    isVisible: Boolean,
    navController: NavHostController,
    viewModel: SetsViewModel
) {
    val items = viewModel.selectedList.size
    var showDeleteDialog by remember { mutableStateOf(false) }
    if (showDeleteDialog) {
        ConfirmDialog(
            title = stringResource(R.string.dialog_title_remove) + " $items " + stringResource(R.string.dialog_title_sets),
            message = stringResource(R.string.dialog_msg_remove_sets_conf),
            confirmText = stringResource(R.string.dialog_btn_remove),
            closeDialog = { showDeleteDialog = false },
            onConfirm = {
                viewModel.removeSelectedSets()
                showDeleteDialog = false
            }
        )
    }

    fun putSelectedToBackStack() {
        navController.currentBackStackEntry?.savedStateHandle?.set(
            key = "setsIds",
            value = viewModel.getSelectedIds()
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
                SelectedCounter(count = viewModel.selectedList.size)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    DrawerItems.setsMultiSelectBarItems.forEach { item ->
                        IconButton(
                            onClick = {
                                when (item.id) {
                                    DrawerItems.Tests.id -> {
                                        putSelectedToBackStack()
                                        navController.navigate(Screen.Tests.route)
                                    }
                                    DrawerItems.Writing.id -> {
                                        putSelectedToBackStack()
                                        navController.navigate(Screen.Writing.route)
                                    }
                                    DrawerItems.FlashCards.id -> {
                                        putSelectedToBackStack()
                                        navController.navigate(Screen.FlashCards.route)
                                    }
                                    DrawerItems.Close.id -> {
                                        viewModel.setTopBar(SetsTopBar.Default)
                                        viewModel.clearSelected()
                                    }
                                    DrawerItems.DeleteForever.id -> showDeleteDialog = true
                                }
                            }
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.id,
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectedCounter(count: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(R.string.label_selected_items),
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = count.toString(),
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp
        )
    }
}