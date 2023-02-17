package com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.CustomSnackbar
import com.kappdev.wordbook.core.presentation.components.CustomTopBarWithBackBtn
import com.kappdev.wordbook.core.presentation.components.LoadingDialog
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.AddEditTermBottomSheet
import com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.AddEditTermViewModel
import com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.TermAction
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterialApi
@Composable
fun AddEditTermScreen(
    navController: NavHostController,
    viewModel: AddEditTermViewModel = hiltViewModel(),
) {
    val isLoading = viewModel.isLoading.value
    val snackbarState = viewModel.snackbarState.value
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    fun finish() {
        viewModel.selectedItem.value?.let { set ->
            navController.navigate(
                Screen.Terms.route + "?setId=${set.setId}"
            ) { popUpTo(0) }
        }
    }

    if (snackbarState.isVisible && snackbarState.message.isNotEmpty()) {
        val actionLabel = stringResource(R.string.label_dismiss)
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = snackbarState.message,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Short
            )
        }
        viewModel.makeSnackbarInvisibility()
    }

    LaunchedEffect(key1 = true) {
        viewModel.onLaunch(onWrongData = { navController.popBackStack() })
    }

    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var currentBottomSheet by remember { mutableStateOf<AddEditTermBottomSheet?>(null) }
    fun hideSheet() {
        scope.launch { bottomSheetState.hide() }
    }
    fun openSheet(bottomSheet: AddEditTermBottomSheet) {
        currentBottomSheet = bottomSheet
        scope.launch { bottomSheetState.show() }
    }
    if(!bottomSheetState.isVisible) currentBottomSheet = null

    ModalBottomSheetLayout(
        sheetState =  bottomSheetState,
        scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.16f),
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetContent = {
            when(currentBottomSheet) {
                is AddEditTermBottomSheet.SelectPhotoSource -> {
                    PickPhotoBottomSheet(
                        viewModel,
                        hideSheet = { hideSheet() },
                        pasteLinkBS = { openSheet(AddEditTermBottomSheet.PasteLink) }
                    )
                }
                is AddEditTermBottomSheet.PasteLink -> {
                    PasteLinkBottomSheet(
                        hideSheet = { hideSheet() },
                        onApply = { url -> viewModel.getImageFromUrl(url) }
                    )
                }
                else -> Box(Modifier.height(1.dp))
            }
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                val titleNewTerm = stringResource(R.string.title_new_term)
                val titleEditTerm = stringResource(R.string.title_edit_term)
                CustomTopBarWithBackBtn(
                    title = if (viewModel.action == TermAction.ADD) titleNewTerm else titleEditTerm,
                    onBackPress = { navController.popBackStack() },
                    actions = {
                        IconButton(
                            onClick = {
                                viewModel.insertTerm { finish() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "done icon",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                )
            },
            snackbarHost = { scaffoldState.snackbarHostState }
        ) {

            AddEditTermContent(viewModel) {
                openSheet(AddEditTermBottomSheet.SelectPhotoSource)
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CustomSnackbar(
                    snackbarHostState = scaffoldState.snackbarHostState,
                    type = snackbarState.snackbarType,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onActionClick = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() }
                )
            }

            if (isLoading) LoadingDialog()
        }
    }
}