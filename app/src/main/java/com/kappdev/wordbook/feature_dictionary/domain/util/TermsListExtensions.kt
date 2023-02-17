package com.kappdev.wordbook.feature_dictionary.domain.util

import com.kappdev.wordbook.feature_dictionary.domain.model.Term

fun MutableList<Term>.containsItem(term: Term): Boolean {
    this.forEach { currentTerm ->
        if (currentTerm.termId == term.termId)
            return true
    }
    return false
}