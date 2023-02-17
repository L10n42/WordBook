package com.kappdev.wordbook.feature_dictionary.presentation.sets.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.components.DefaultRadioButton
import com.kappdev.wordbook.feature_dictionary.domain.util.OrderType
import com.kappdev.wordbook.feature_dictionary.domain.util.SetOrder
import com.kappdev.wordbook.feature_dictionary.presentation.sets.SetsViewModel

@Composable
fun SetsOrderSheet(
    viewModel: SetsViewModel
) {
    val setsOrder = viewModel.setsOrder.value
    val onOrderChange: (SetOrder) -> Unit = { newOrder ->
        viewModel.changeOrderAndGetSets(newOrder)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ).padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.os_title_ascending),
                selected = setsOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(setsOrder.copy(orderType = OrderType.Ascending))
                }
            )
            DefaultRadioButton(
                text = stringResource(id = R.string.os_title_descending),
                selected = setsOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(setsOrder.copy(orderType = OrderType.Descending))
                }
            )
        }

        Divider(
            Modifier
                .fillMaxWidth(0.95f)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DefaultRadioButton(
                    text = stringResource(id = R.string.os_title_by_name),
                    selected = setsOrder is SetOrder.Name,
                    onSelect = { onOrderChange(SetOrder.Name(setsOrder.orderType)) }
                )
                DefaultRadioButton(
                    text = stringResource(id = R.string.os_title_by_description),
                    selected = setsOrder is SetOrder.Description,
                    onSelect = { onOrderChange(SetOrder.Description(setsOrder.orderType)) }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DefaultRadioButton(
                    text = stringResource(id = R.string.os_title_by_date),
                    selected = setsOrder is SetOrder.Date,
                    onSelect = { onOrderChange(SetOrder.Date(setsOrder.orderType)) }
                )
                DefaultRadioButton(
                    text = stringResource(id = R.string.os_title_by_items),
                    selected = setsOrder is SetOrder.Items,
                    onSelect = { onOrderChange(SetOrder.Items(setsOrder.orderType)) }
                )
            }
        }
    }
}