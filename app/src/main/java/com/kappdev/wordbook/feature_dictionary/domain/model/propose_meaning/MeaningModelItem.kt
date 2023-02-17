package com.kappdev.wordbook.feature_dictionary.domain.model.propose_meaning

data class MeaningModelItem(
    val meanings: List<Meaning>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val word: String?
)