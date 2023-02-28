package com.kappdev.wordbook.feature_dictionary.domain.util

sealed class SetOrder(val orderType: OrderType, val id: String) {
    class Name(orderType: OrderType): SetOrder(orderType, NAME_ID)
    class Description(orderType: OrderType): SetOrder(orderType, DESCRIPTION_ID)
    class Items(orderType: OrderType): SetOrder(orderType, ITEMS_ID)
    class Date(orderType: OrderType): SetOrder(orderType, DATE_ID)

    fun copy(orderType: OrderType): SetOrder {
        return when(this) {
            is Name -> Name(orderType)
            is Description -> Description(orderType)
            is Items -> Items(orderType)
            is Date -> Date(orderType)
        }
    }

    companion object {
        fun getById(id: String, orderType: OrderType?): SetOrder? {
            val type = orderType?: OrderType.Ascending
            return when (id) {
                NAME_ID -> Name(type)
                DESCRIPTION_ID -> Description(type)
                ITEMS_ID -> Items(type)
                DATE_ID -> Date(type)
                else -> null
            }
        }

        private const val NAME_ID = "by_name"
        private const val DESCRIPTION_ID = "by_description"
        private const val ITEMS_ID = "by_items"
        private const val DATE_ID = "by_date"
    }
}
