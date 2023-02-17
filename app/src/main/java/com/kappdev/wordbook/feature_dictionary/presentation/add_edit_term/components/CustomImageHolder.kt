package com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.components

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.core.presentation.components.ImageLoadingError

@Composable
fun CustomImageHolder(
    imageUri: String?,
    image: Bitmap?,
    isLoading: Boolean,
    removeImage: () -> Unit
) {
    Log.d("Image", "height: ${image?.height}; width: ${image?.width}")
    Box(
        modifier = Modifier.wrapContentSize().padding(bottom = 13.dp, start = 13.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.align(Alignment.Center).padding(top = 16.dp)
                )
            }
            !isLoading && image == null && imageUri != null -> {
                ImageLoadingError()
            }
            !isLoading && image != null -> {
                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = "photo",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .heightIn(100.dp, 200.dp)
                        .padding(top = 13.dp, end = 13.dp)
                        .border(
                            width = 1.dp,
                            shape = RectangleShape,
                            color = MaterialTheme.colors.onBackground
                        )
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(26.dp)
                        .background(
                            shape = CircleShape,
                            color = MaterialTheme.colors.background
                        )
                        .border(
                            width = 1.dp,
                            shape = CircleShape,
                            color = MaterialTheme.colors.onBackground
                        )
                        .clickable { removeImage() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "remove_image_icon",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}