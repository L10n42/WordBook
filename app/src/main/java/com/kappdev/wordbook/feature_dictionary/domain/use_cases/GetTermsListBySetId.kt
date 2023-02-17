package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository

class GetTermsListBySetId(
    private val repository: DictionaryRepository
) {
    operator fun invoke(setId: String): List<Term> {
        return repository.getAllTermsBySetIdList(setId)
    }
}