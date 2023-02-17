package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository

class MoveTermToSet(
    private val repository: DictionaryRepository
) {

    suspend operator fun invoke(newSetID: String, term: Term) {
        val newValue = term.copy(setId = newSetID)

        repository.updateTerm(newValue)
        repository.incrementSetNumber(newSetID)
        repository.decrementSetNumber(term.setId)
    }

    suspend operator fun invoke(newSetID: String, terms: List<Term>) {
        terms.forEach { term ->
            val newValue = term.copy(setId = newSetID)

            repository.updateTerm(newValue)
            repository.incrementSetNumber(newSetID)
            repository.decrementSetNumber(term.setId)
        }
    }

}