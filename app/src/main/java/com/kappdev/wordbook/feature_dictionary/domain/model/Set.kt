package com.kappdev.wordbook.feature_dictionary.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sets_table")
data class Set(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "set_id")
    val setId: String,
    val name: String,
    val description: String,
    @ColumnInfo(name = "timestamp", defaultValue = "0")
    val timestamp: Long,
    val number: Int = 0
) {
    companion object {
        val EmptySet = Set(
            setId = "",
            name = "",
            description = "",
            timestamp = 0
        )
    }
}

