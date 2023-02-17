package com.kappdev.wordbook.feature_dictionary.domain.model.propose_meaning

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)