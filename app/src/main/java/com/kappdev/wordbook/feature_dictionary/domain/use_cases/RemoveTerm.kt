package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository

class RemoveTerm(
    private val repository: DictionaryRepository
) {

    suspend operator fun invoke(term: Term) {
        repository.removeTerm(term)
        repository.decrementSetNumber(term.setId)
    }

    suspend operator fun invoke(terms: List<Term>) {
        terms.forEach { term ->
            repository.removeTerm(term)
            repository.decrementSetNumber(term.setId)
        }
    }
}