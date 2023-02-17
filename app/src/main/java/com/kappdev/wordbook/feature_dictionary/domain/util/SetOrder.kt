package com.kappdev.wordbook.feature_dictionary.domain.util

sealed class SetOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): SetOrder(orderType)
    class Description(orderType: OrderType): SetOrder(orderType)
    class Items(orderType: OrderType): SetOrder(orderType)
    class Date(orderType: OrderType): SetOrder(orderType)

    fun copy(orderType: OrderType): SetOrder {
        return when(this) {
            is Name -> Name(orderType)
            is Description -> Description(orderType)
            is Items -> Items(orderType)
            is Date -> Date(orderType)
        }
    }
}
