package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.model.CardTerm

class TermsSearch {

    fun searchIn(list: List<CardTerm>, value: String): List<CardTerm> {
        if (value.trim().isNotBlank()) {
            return list.mapNotNull { cardTerm ->
                if (cardTerm.term.term.lowercase().contains(value.trim().lowercase()))
                    cardTerm
                else null
            }
        } else return emptyList()
    }
}