package com.kappdev.wordbook.feature_dictionary.presentation.terms

import com.kappdev.wordbook.feature_dictionary.domain.model.Term

sealed class TermsBottomSheet {
    object Order: TermsBottomSheet()
    class More(val term: Term): TermsBottomSheet()
}
