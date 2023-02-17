package com.kappdev.wordbook.feature_dictionary.domain.util

sealed class TermOrder(val orderType: OrderType) {
    class Term(orderType: OrderType): TermOrder(orderType)
    class Definition(orderType: OrderType): TermOrder(orderType)
    class Date(orderType: OrderType): TermOrder(orderType)

    fun copy(orderType: OrderType): TermOrder {
        return when(this) {
            is Term -> Term(orderType)
            is Definition -> Definition(orderType)
            is Date -> Date(orderType)
        }
    }
}
