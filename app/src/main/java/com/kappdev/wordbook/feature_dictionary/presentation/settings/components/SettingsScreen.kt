package com.kappdev.wordbook.feature_dictionary.presentation.settings.components

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.core.presentation.components.CustomSnackbar
import com.kappdev.wordbook.core.presentation.components.CustomTopBarWithBackBtn
import com.kappdev.wordbook.core.presentation.components.LoadingDialog
import com.kappdev.wordbook.feature_dictionary.presentation.settings.SettingsViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val snackbarState = viewModel.snackbarState.value
    val isLoading = viewModel.isLoading.value
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val importDatabaseLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.importDatabase(uri)
        }
    }

    val importSetLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.importSet(uri)
        }
    }

    if(snackbarState.isVisible) {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = snackbarState.message,
                duration = SnackbarDuration.Short
            )
        }
        viewModel.setSnackbarVisibility(false)
    }

    val defDivider: @Composable () -> Unit = {
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp))
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopBarWithBackBtn(
                title = stringResource(R.string.title_settings),
                onBackPress = { navController.navigate(Screen.Sets.route) { popUpTo(0) } }
            )
        },
        snackbarHost = { scaffoldState.snackbarHostState }
    ) { innerPadding ->
        if (isLoading) LoadingDialog()

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            item { AppLanguageCard(viewModel) }

            item { defDivider() }

            item { SpeechLanguageCard(viewModel) }

            item { defDivider() }

            item { SpeechSpeedCard(viewModel) }

            item { defDivider() }

            item {
                SwitcherItem(
                    titleRes = R.string.settings_title_dark_theme,
                    isChecked = viewModel.isThemeDark(),
                    onCheckedChange = { theme -> viewModel.setTheme(theme) }
                )
            }

            item { defDivider() }

            item { PrimaryColorCard(viewModel) }

            item { defDivider() }

            item {
                SettingItemWithDescription(
                    titleRes = R.string.db_export_title,
                    descriptionRes = R.string.db_export_description,
                    onClick = { viewModel.exportDatabase() }
                )
            }

            item { defDivider() }

            item {
                SettingItemWithDescription(
                    titleRes = R.string.db_import_title,
                    descriptionRes = R.string.db_import_description,
                    onClick = { importDatabaseLauncher.launch("application/zip") }
                )
            }

            item { defDivider() }

            item {
                SettingItemWithDescription(
                    titleRes = R.string.set_import_title,
                    descriptionRes = R.string.set_import_description,
                    onClick = { importSetLauncher.launch("application/json") }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            CustomSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                type = snackbarState.snackbarType,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}