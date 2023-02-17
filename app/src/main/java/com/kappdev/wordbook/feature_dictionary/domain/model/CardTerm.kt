package com.kappdev.wordbook.feature_dictionary.domain.model

import android.graphics.Bitmap
import java.io.Serializable

data class CardTerm(
    val term: Term,
    var imageBitmap: Bitmap?
) : Serializable
