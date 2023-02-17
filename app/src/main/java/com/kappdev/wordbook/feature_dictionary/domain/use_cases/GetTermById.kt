package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository

class GetTermById(
    private val repository: DictionaryRepository
) {

    suspend operator fun invoke(termId: String): Term? {
        return repository.getTermById(termId)
    }

}