package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository

class ClearTable(
    private val repository: DictionaryRepository
) {

    operator fun invoke(sets: Boolean = false, terms: Boolean = false) {
        if (sets) repository.removeAllSets()
        if (terms) repository.removeAllTerms()
    }

}