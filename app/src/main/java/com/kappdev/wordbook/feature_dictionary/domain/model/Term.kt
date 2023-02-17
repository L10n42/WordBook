package com.kappdev.wordbook.feature_dictionary.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terms_table")
data class Term(
    @ColumnInfo(name = "set_id")
    val setId: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "term_id")
    val termId: String,
    val term: String,
    val definition: String,
    val transcription: String,
    val example: String,
    @ColumnInfo(name = "timestamp", defaultValue = "0")
    val timestamp: Long,
    @ColumnInfo(name = "image")
    val image: String?
) {
    companion object {
        val EmptyTerm = Term(
            setId = "",
            termId = "",
            term = "",
            definition = "",
            transcription = "",
            example = "",
            timestamp = 0,
            image = null
        )
    }
}
