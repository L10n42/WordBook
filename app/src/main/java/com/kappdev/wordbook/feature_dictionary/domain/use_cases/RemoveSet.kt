package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.model.Set
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository

class RemoveSet(
    private val repository: DictionaryRepository
) {

    suspend operator fun invoke(set: Set) {
        repository.removeSet(set)
        repository.removeAllTermsBySet(set.setId)
    }

    suspend operator fun invoke(sets: List<Set>) {
        sets.forEach { set ->
            repository.removeSet(set)
        }
    }

}