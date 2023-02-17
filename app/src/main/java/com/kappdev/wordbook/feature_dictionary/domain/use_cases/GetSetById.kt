package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository
import com.kappdev.wordbook.feature_dictionary.domain.model.Set

class GetSetById(
    private val repository: DictionaryRepository
) {

    suspend operator fun invoke(setId: String): Set? {
        return repository.getSetById(setId)
    }

}