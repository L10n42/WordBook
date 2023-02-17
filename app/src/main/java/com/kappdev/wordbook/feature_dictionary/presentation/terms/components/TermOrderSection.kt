package com.kappdev.wordbook.feature_dictionary.presentation.terms.components

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
import com.kappdev.wordbook.feature_dictionary.domain.util.TermOrder
import com.kappdev.wordbook.feature_dictionary.presentation.terms.TermViewModel

@Composable
fun TermOrderSection(
    viewModel: TermViewModel
) {
    val termOrder = viewModel.termsOrder.value
    val onOrderChange: (TermOrder) -> Unit = { newOrder ->
        viewModel.changeOrderAndGetTerms(newOrder)
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
                selected = termOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(termOrder.copy(orderType = OrderType.Ascending))
                }
            )
            DefaultRadioButton(
                text = stringResource(id = R.string.os_title_descending),
                selected = termOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(termOrder.copy(orderType = OrderType.Descending))
                }
            )
        }

        Divider(
            Modifier
                .fillMaxWidth(0.95f)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DefaultRadioButton(
                    text = stringResource(id = R.string.os_title_by_term),
                    selected = termOrder is TermOrder.Term,
                    onSelect = { onOrderChange(TermOrder.Term(termOrder.orderType)) }
                )
                DefaultRadioButton(
                    text = stringResource(id = R.string.os_title_by_definition),
                    selected = termOrder is TermOrder.Definition,
                    onSelect = { onOrderChange(TermOrder.Definition(termOrder.orderType)) }
                )
            }

            DefaultRadioButton(
                text = stringResource(id = R.string.os_title_by_date),
                selected = termOrder is TermOrder.Date,
                onSelect = { onOrderChange(TermOrder.Date(termOrder.orderType)) }
            )
        }
    }
}