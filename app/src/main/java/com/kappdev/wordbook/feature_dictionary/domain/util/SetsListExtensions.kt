package com.kappdev.wordbook.feature_dictionary.domain.util

import com.kappdev.wordbook.feature_dictionary.domain.model.Set

fun MutableList<Set>.containsItem(set: Set): Boolean {
    this.forEach { currentSet ->
        if (currentSet.setId == set.setId)
            return true
    }
    return false
}