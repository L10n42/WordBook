package com.kappdev.wordbook.feature_dictionary.domain.use_cases

data class ImExDatabaseUseCases(
    val exportDatabase: ExportDatabase,
    val importDatabase: ImportDatabase,
    val exportSet: ExportSet,
    val importSet: ImportSet,
)
