package com.kappdev.wordbook.feature_dictionary.presentation.terms

import com.kappdev.wordbook.feature_dictionary.domain.model.Term

sealed class TermsDialog {
    class MoveTo(val term: Term): TermsDialog()
    class Remove(val term: Term): TermsDialog()
}
