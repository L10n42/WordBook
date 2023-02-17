package com.kappdev.wordbook.core.presentation.permissions

import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.kappdev.wordbook.R
import com.google.accompanist.permissions.*

@ExperimentalPermissionsApi
@Composable
fun RequestMultiplePermissions(
    permissions: List<String>,
    message: String = stringResource(R.string.request_permissions_message),
    content: @Composable () -> Unit
) {
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions = permissions)

    HandleRequests(
        multiplePermissionsState = multiplePermissionsState,
        deniedContent = {
            PermissionRequestContent(
                message = message,
                onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }
            )
        },

        content = {
            content()
        }
    )
}

@ExperimentalPermissionsApi
@Composable
private fun HandleRequests(
    multiplePermissionsState: MultiplePermissionsState,
    deniedContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val result = multiplePermissionsState.permissions.all { permissionState ->
        permissionState.status == PermissionStatus.Granted
    }

    if (result){
        content()
    } else {
        deniedContent()
    }
}