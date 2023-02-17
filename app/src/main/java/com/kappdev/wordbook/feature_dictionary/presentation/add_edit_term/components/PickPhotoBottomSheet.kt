package com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.AddEditTermViewModel

@ExperimentalComposeUiApi
@Composable
fun PickPhotoBottomSheet(
    viewModel: AddEditTermViewModel,
    hideSheet: () -> Unit,
    pasteLinkBS: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent())
    { uri: Uri? ->
        if (uri != null) {
            viewModel.getImageFromStorage(uri)
            hideSheet()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
            )
            .padding(bottom = 32.dp, top = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.select_photo_source_title),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            ChoiceButton(
                icon = Icons.Default.PhoneAndroid,
                label = stringResource(R.string.btn_device)
            ) {
                launcher.launch("image/*")
            }
            ChoiceButton(
                icon = Icons.Default.Link,
                label = stringResource(R.string.btn_link)
            ) {
                hideSheet()
                pasteLinkBS()
            }
        }
    }
}

@Composable
private fun ChoiceButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(8.dp)
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.size(36.dp)
            )
        }

        Text(
            text = label,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onSurface
        )
    }
}