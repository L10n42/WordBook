package com.kappdev.wordbook.feature_dictionary.presentation.terms.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.kappdev.wordbook.core.presentation.components.CardSelectedEffect
import com.kappdev.wordbook.core.presentation.components.SpeakIconWithPopupTranscription
import com.kappdev.wordbook.feature_dictionary.domain.model.CardTerm
import com.kappdev.wordbook.feature_dictionary.domain.util.containsItem
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermViewModel
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermsTopBar

@ExperimentalFoundationApi
@Composable
fun TermCard(
    cardTerm: CardTerm,
    viewModel: TermViewModel,
    showMoreSheet: () -> Unit
) {
    val isMultiSelectModeOn = viewModel.currentTopBar.value == TermsTopBar.MultiSelect
    val isSearchModeOn = viewModel.currentTopBar.value == TermsTopBar.Search
    val selectedItemsList = viewModel.selectedList
    val term = cardTerm.term
    val transcription = term.transcription

    var isSelected by rememberSaveable { mutableStateOf(false) }
    var size by remember { mutableStateOf (Size.Zero) }
    val height = with(LocalDensity.current) { size.height.toDp() }
    val width = with(LocalDensity.current) { size.width.toDp() }

    when {
        (!selectedItemsList.containsItem(term) && isSelected) -> isSelected = false
        (selectedItemsList.containsItem(term) && !isSelected) -> isSelected = true
    }

    val switchOnSelectMode = {
        viewModel.setTopBar(TermsTopBar.MultiSelect)
        viewModel.clearSelected()
        viewModel.selectTerm(term)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
            .onGloballyPositioned { coordinates ->
                size = coordinates.size.toSize()
            }
            .combinedClickable(
                onClick = {
                    if (isMultiSelectModeOn) {
                        if (isSelected) viewModel.deselectTerm(term) else viewModel.selectTerm(term)
                    }
                },
                onLongClick = {
                    if (!isMultiSelectModeOn && !isSearchModeOn) switchOnSelectMode()
                }
            )
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(top = 0.dp, bottom = 16.dp, end = 8.dp, start = 8.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = term.term,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(.8f)
                )

                Row(verticalAlignment = Alignment.Top) {
                    SpeakIconWithPopupTranscription(
                        text = transcription,
                        onIconClick = { viewModel.say(term.term) }
                    )

                    IconButton(
                        onClick = {
                            if (!isMultiSelectModeOn) showMoreSheet()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "term_more_icon",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                cardTerm.imageBitmap?.let { image ->
                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = "term_photo",
                        alignment = Alignment.TopStart,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.height(78.dp).clip(RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = cardTerm.term.definition,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            if (term.example.isNotBlank()) {
                Divider()
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = cardTerm.term.example,
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 2,
                    fontStyle = FontStyle.Italic,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        CardSelectedEffect(
            isVisible = isSelected && isMultiSelectModeOn,
            width = width,
            height = height
        )
    }
}
