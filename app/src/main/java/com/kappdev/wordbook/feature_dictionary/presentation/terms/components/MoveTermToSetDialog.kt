package com.kappdev.wordbook.feature_dictionary.presentation.terms.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermViewModel

@Composable
fun MoveTermToSetDialog(
    closeDialog: () -> Unit,
    term: Term? = null,
    multipleMove: Boolean = false,
    viewModel: TermViewModel
) {
    val list = viewModel.sets.value

    Dialog(onDismissRequest = closeDialog) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(26.dp),
            ) {
                Text(
                    text = stringResource(R.string.dialog_title_move_to),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )

                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = closeDialog
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close_button",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            }

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(list) { set ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            when {
                                (multipleMove && term == null) -> viewModel.multipleMoveTo(set.setId)
                                (!multipleMove && term != null) -> viewModel.singleMoveTo(
                                    set.setId,
                                    term
                                )
                            }
                            closeDialog()
                        }
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = set.name,
                                fontSize = 16.sp,
                                color = MaterialTheme.colors.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            if (set.description.isNotBlank()) {
                                Text(
                                    text = set.description,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colors.onBackground,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}