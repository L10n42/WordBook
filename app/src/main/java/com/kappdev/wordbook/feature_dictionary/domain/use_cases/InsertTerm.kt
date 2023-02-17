package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import android.content.Context
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository
import com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term.TermAction

class InsertTerm(
    private val context: Context,
    private val repository: DictionaryRepository
) {

    suspend operator fun invoke(term: Term, operation: TermAction) {
        return when {
            term.term.isBlank() -> throw Exception(msg(R.string.error_empty_term_field))
            term.definition.isBlank() -> throw Exception(msg(R.string.error_empty_def_field))
            term.termId.isBlank() -> throw Exception(msg(R.string.error_something_went_wrong))
            term.setId.isBlank() -> throw Exception(msg(R.string.error_something_went_wrong))

            else -> {
                try {
                    when (operation) {
                        TermAction.EDIT -> repository.updateTerm(term)
                        TermAction.ADD -> {
                            repository.addTerm(term)
                            repository.incrementSetNumber(term.setId)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    throw Exception(msg(R.string.error_card_inserting))
                }
            }
        }
    }
    private fun msg(id: Int): String {
        return context.getString(id)
    }
}