package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.model.Set
import com.kappdev.wordbook.feature_dictionary.domain.util.OrderType
import com.kappdev.wordbook.feature_dictionary.domain.util.SetOrder

class SortSets {

    operator fun invoke(sets: List<Set>, order: SetOrder): List<Set> {
        return when(order.orderType) {
            is OrderType.Ascending -> {
                when(order) {
                    is SetOrder.Name -> sets.sortedBy { it.name.lowercase() }
                    is SetOrder.Description -> sets.sortedBy { it.description.lowercase() }
                    is SetOrder.Items -> sets.sortedBy { it.number }
                    is SetOrder.Date -> sets.sortedBy { it.timestamp }
                }
            }
            is OrderType.Descending -> {
                when(order) {
                    is SetOrder.Name -> sets.sortedByDescending { it.name.lowercase() }
                    is SetOrder.Description -> sets.sortedByDescending { it.description.lowercase() }
                    is SetOrder.Items -> sets.sortedByDescending { it.number }
                    is SetOrder.Date -> sets.sortedByDescending { it.timestamp }
                }
            }
        }
    }
}