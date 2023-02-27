package com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.feature_dictionary.domain.model.CardTerm

@Composable
fun BackCard(
    modifier: Modifier = Modifier,
    item: CardTerm,
    definitionFontSize: TextUnit = 20.sp,
    exampleFontSize: TextUnit = 16.sp,
    imageHeight: Dp = 150.dp,
    isExampleVisible: Boolean = true,
    isImageVisible: Boolean = true,
) {
    val image = item.imageBitmap
    val definition = item.term.definition
    val example = item.term.example

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        if (image != null && isImageVisible) {
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = "term_photo",
                alignment = Alignment.TopStart,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(imageHeight)
                    .clip(RoundedCornerShape(4.dp))
            )
        }

        Text(
            text = definition,
            fontSize = definitionFontSize,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        if (example.isNotBlank() && isExampleVisible) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Divider()
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = example,
                    fontSize = exampleFontSize,
                    color = MaterialTheme.colors.onBackground,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}