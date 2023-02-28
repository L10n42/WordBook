package com.kappdev.wordbook.feature_dictionary.presentation.sets.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.core.presentation.components.CardSelectedEffect
import com.kappdev.wordbook.feature_dictionary.domain.util.containsItem
import com.kappdev.wordbook.feature_dictionary.domain.model.Set
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsTopBar
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsViewModel

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun SetCard(
    set: Set,
    navController: NavHostController,
    viewModel: SetsViewModel,
    showMoreSheet: () -> Unit
) {
    val isMultiSelectModeOn = viewModel.currentTopBar.value == SetsTopBar.MultiSelect
    val isSearchModeOn = viewModel.currentTopBar.value == SetsTopBar.Search
    val selectedItemsList = viewModel.selectedList
    var isSelected by rememberSaveable { mutableStateOf(false) }
    var size by remember { mutableStateOf(Size.Zero) }
    val height = with(LocalDensity.current) { size.height.toDp() }
    val width = with(LocalDensity.current) { size.width.toDp() }

    when {
        (!selectedItemsList.containsItem(set) && isSelected) -> isSelected = false
        (selectedItemsList.containsItem(set) && !isSelected) -> isSelected = true
    }

    val switchOnSelectMode = {
        viewModel.setTopBar(SetsTopBar.MultiSelect)
        viewModel.clearSelected()
        viewModel.selectSet(set)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
            .onGloballyPositioned { coordinates ->
                size = coordinates.size.toSize()
            }
            .combinedClickable(
                onClick = {
                    when {
                        isMultiSelectModeOn ->
                            if (isSelected) viewModel.deselectSet(set) else viewModel.selectSet(set)
                        else ->
                            navController.navigate(Screen.Terms.route + "?setId=${set.setId}") { popUpTo(0) }
                    }
                },
                onLongClick = {
                    if (!isMultiSelectModeOn && !isSearchModeOn) switchOnSelectMode()
                }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp, bottom = 8.dp, end = 8.dp, start = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = set.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(.9f)
                )

                IconButton(onClick = showMoreSheet) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "more_icon",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            }

            if (set.description != "") {
                Text(
                    text = set.description,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            val containText = stringResource(id = R.string.set_card_contains)
            val cardsText = stringResource(id = R.string.set_card_cards)
            Text(
                text = "$containText ${set.number} $cardsText",
                fontSize = 16.sp,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    navController.navigate(Screen.AddEditTerm.route + "?setId=${set.setId}")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add_icon",
                    tint = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.btn_add_new_card),
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold,
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