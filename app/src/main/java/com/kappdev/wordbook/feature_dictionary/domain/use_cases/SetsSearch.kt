package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.model.Set

class SetsSearch {

     fun searchIn(list: List<Set>, value: String): List<Set> {
        if (value.trim().isNotBlank()) {
            return list.mapNotNull { set ->
                if (set.name.lowercase().contains(value.trim().lowercase()))
                    set
                else null
            }
        } else
            return emptyList()
    }
}