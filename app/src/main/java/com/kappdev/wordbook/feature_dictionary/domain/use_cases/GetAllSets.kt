package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository
import com.kappdev.wordbook.feature_dictionary.domain.model.Set
import kotlinx.coroutines.flow.Flow

class GetAllSets(
    private val repository: DictionaryRepository
) {

    fun list(): List<Set> {
        return repository.getSetsList().sortedBy { it.name.lowercase() }
    }

    fun flow(): Flow<List<Set>> {
        return repository.getAllSets()
    }

}