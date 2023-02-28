package com.kappdev.wordbook.feature_dictionary.domain.util

sealed class TermOrder(val orderType: OrderType, val id: String) {
    class Term(orderType: OrderType): TermOrder(orderType, TERM_ID)
    class Definition(orderType: OrderType): TermOrder(orderType, DEFINITION_ID)
    class Date(orderType: OrderType): TermOrder(orderType, DATE_ID)

    fun copy(orderType: OrderType): TermOrder {
        return when(this) {
            is Term -> Term(orderType)
            is Definition -> Definition(orderType)
            is Date -> Date(orderType)
        }
    }

    companion object {
        fun getById(id: String, orderType: OrderType?): TermOrder? {
            val type = orderType?: OrderType.Ascending
            return when (id) {
                TERM_ID -> Term(type)
                DEFINITION_ID -> Definition(type)
                DATE_ID -> Date(type)
                else -> null
            }
        }

        private const val TERM_ID = "by_term"
        private const val DEFINITION_ID = "by_definition"
        private const val DATE_ID = "by_date"
    }
}
