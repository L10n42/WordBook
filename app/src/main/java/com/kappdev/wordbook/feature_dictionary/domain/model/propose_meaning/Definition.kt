package com.kappdev.wordbook.feature_dictionary.domain.model.propose_meaning

data class Definition(
    val antonyms: List<Any>?,
    val definition: String?,
    val example: String?,
    val synonyms: List<Any>?
)