package com.kappdev.wordbook.feature_dictionary.domain.model

data class SetJSON(
    val setId: String,
    val name: String,
    val description: String,
    val timestamp: Long,
    val number: Int,
    val terms: List<Term>
) {
    fun getSet(): Set {
        return Set(setId, name, description, timestamp, number)
    }

    companion object {
        fun from(set: Set, terms: List<Term>): SetJSON {
            return SetJSON(
                setId = set.setId,
                name = set.name,
                description = set.description,
                timestamp = set.timestamp,
                number = set.number,
                terms = terms
            )
        }
    }
}
