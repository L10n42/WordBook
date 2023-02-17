package com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.ConfirmDialog
import com.kappdev.wordbook.core.presentation.components.MTextField
import com.kappdev.wordbook.core.presentation.components.OutlineDropDown
import com.kappdev.wordbook.feature_dictionary.domain.util.ImageConverter
import com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.AddEditTermViewModel
import com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.TermAction

@Composable
fun AddEditTermContent(
    viewModel: AddEditTermViewModel,
    onPickImage: () -> Unit
) {
    val isImageLoadingState = viewModel.isImageLoading.value
    val selectedItem = viewModel.selectedItem.value
    val fieldsState = viewModel.fieldsState.value
    val listOfAllSets = viewModel.sets
    val image = ImageConverter().stringToBitmap(fieldsState.image)

    var showPMConfirmDialog by remember { mutableStateOf(false) }
    if (showPMConfirmDialog) {
        ConfirmDialog(
            title = stringResource(R.string.dialog_title_warning),
            message = stringResource(R.string.dialog_msg_propose_meaning_conf),
            confirmText = stringResource(R.string.dialog_btn_continue),
            closeDialog = { showPMConfirmDialog = false },
            onConfirm = {
                viewModel.proposeTermMeaning()
                showPMConfirmDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val action = viewModel.action
        if (action == TermAction.ADD) {
            OutlineDropDown(
                itemsList = listOfAllSets,
                modifier = Modifier.padding(all = 8.dp),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colors.onBackground,
                selectedValue = selectedItem?.name ?: "",
                onItemSelected = { set ->
                    viewModel.setSelectedSet(set)
                }
            ) { set ->
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

        Spacer(modifier = Modifier.height(4.dp))
        MTextField(
            text = fieldsState.term,
            hint = stringResource(id = R.string.label_term),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
            onTextChanged = { newText -> viewModel.setTerm(newText) }
        )
        MTextField(
            text = fieldsState.transcription,
            hint = stringResource(id = R.string.label_trans),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
            onTextChanged = { newText -> viewModel.setTranscription(newText) }
        )
        MTextField(
            text = fieldsState.definition,
            hint = stringResource(id = R.string.label_def),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
            onTextChanged = { newText -> viewModel.setDefinition(newText) }
        )
        MTextField(
            text = fieldsState.example,
            hint = stringResource(id = R.string.label_ex),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
            onTextChanged = { newText -> viewModel.setExample(newText) }
        )

        ActionButtons(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
            pickImage = onPickImage,
            proposeMeaning = {
                if (fieldsState.transcription.trim() != "" || fieldsState.definition.trim() != "" || fieldsState.example.trim() != "") {
                    showPMConfirmDialog = true
                } else viewModel.proposeTermMeaning()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        CustomImageHolder(
            imageUri = fieldsState.image,
            image = image,
            isLoading = isImageLoadingState,
            removeImage = { viewModel.setImage(null) }
        )
    }
}

@Composable
private fun ActionButtons(
    modifier: Modifier = Modifier,
    pickImage: () -> Unit,
    proposeMeaning: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        Button(
            shape = RoundedCornerShape(16.dp),
            onClick = pickImage
        ) {
            Text(
                text = stringResource(R.string.btn_pick_photo_title),
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            shape = RoundedCornerShape(16.dp),
            onClick = proposeMeaning
        ) {
            Text(
                text = stringResource(R.string.btn_propose_meaning),
                fontWeight = FontWeight.Bold
            )
        }
    }
}