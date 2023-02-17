package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.model.Set
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository

class UpdateSet(
    private val repository: DictionaryRepository
) {

    suspend operator fun invoke(set: Set) {
        repository.updateSet(set)
    }

}