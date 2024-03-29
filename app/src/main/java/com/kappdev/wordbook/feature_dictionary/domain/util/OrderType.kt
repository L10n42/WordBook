package com.kappdev.wordbook.feature_dictionary.domain.util

sealed class OrderType(val id: String) {
    object Ascending: OrderType(ASCENDING_ID)
    object Descending: OrderType(DESCENDING_ID)

    companion object {
        fun getById(id: String): OrderType? {
            return when (id) {
                ASCENDING_ID -> Ascending
                DESCENDING_ID -> Descending
                else -> null
            }
        }

        private const val ASCENDING_ID = "ascending"
        private const val DESCENDING_ID = "descending"
    }
}
