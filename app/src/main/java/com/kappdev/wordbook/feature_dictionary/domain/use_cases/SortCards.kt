package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.model.CardTerm
import com.kappdev.wordbook.feature_dictionary.domain.util.OrderType
import com.kappdev.wordbook.feature_dictionary.domain.util.TermOrder

class SortCards {

    operator fun invoke(cards: List<CardTerm>, order: TermOrder): List<CardTerm> {
        return when(order.orderType) {
            is OrderType.Ascending -> {
                when(order) {
                    is TermOrder.Term -> cards.sortedBy { it.term.term.lowercase() }
                    is TermOrder.Date -> cards.sortedBy { it.term.timestamp }
                    is TermOrder.Definition -> cards.sortedBy { it.term.definition.lowercase() }
                }
            }
            is OrderType.Descending -> {
                when(order) {
                    is TermOrder.Term -> cards.sortedByDescending { it.term.term.lowercase() }
                    is TermOrder.Date -> cards.sortedByDescending { it.term.timestamp }
                    is TermOrder.Definition -> cards.sortedByDescending { it.term.definition.lowercase() }
                }
            }
        }
    }
}