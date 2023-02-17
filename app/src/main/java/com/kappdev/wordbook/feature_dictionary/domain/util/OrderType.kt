package com.kappdev.wordbook.feature_dictionary.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
