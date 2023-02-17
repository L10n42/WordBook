package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow

class GetTermsByListOfSetsIds(
    private val repository: DictionaryRepository
) {
    operator fun invoke(ids: List<String>) : Flow<List<Term>> {
        return repository.getAllTermsByArrayOfSetsId(ids)
    }
}