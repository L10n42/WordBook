package com.kappdev.wordbook.feature_dictionary.data.util

import java.text.SimpleDateFormat
import java.util.*

class IDGenerator {

    fun generateID() : String {
        val formatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
        val now = Date()

        val randomNumber = (1..4).map { (0..9).random() }.joinToString("")

        return formatter.format(now) + "-$randomNumber"
    }

}