package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow

class GetTermsFlowBySetId(
    private val repository: DictionaryRepository
) {
    operator fun invoke(setId: String) : Flow<List<Term>> {
        return repository.getAllTermsBySetIdFlow(setId)
    }
}