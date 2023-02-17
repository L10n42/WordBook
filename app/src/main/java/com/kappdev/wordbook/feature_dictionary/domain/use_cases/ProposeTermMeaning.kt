package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import android.content.Context
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.data.api.RetrofitInstance
import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import okio.IOException
import retrofit2.HttpException

class ProposeTermMeaning(
    private val context: Context
) {
    private var transcription = ""
    private var example = ""
    private var definition = ""

    suspend operator fun invoke(value: String): Term {
        if (value.trim().isBlank()) {
            throw Exception(msg(R.string.error_empty_term_field))
        }

        val response = try {
            RetrofitInstance.meaningApi.getMeaning(word = value)
        } catch (e: IOException) {
            throw Exception(msg(R.string.error_no_internet_connection))
        } catch (e: HttpException) {
            throw Exception(msg(R.string.error_can_not_find_anything))
        }

        if (response.isNotEmpty()) {

            val definitions = response[0].meanings[0].definitions
            val phonetics = response[0].phonetics

            // get phonetics
            transcription = ""
            phonetics.forEach { phonetic ->

                phonetic.text?.let { text ->
                    if (transcription == "") {
                        transcription = text
                    } else {
                        if (!transcription.contains(text))
                            transcription = transcription.plus(" | $text")
                    }
                }
            }

            // get examples
            example = ""
            definitions.forEachIndexed { index, definition ->
                if (index > 1) return@forEachIndexed

                definition.example?.let { ex ->
                    example = if (example == "") "- $ex" else example.plus("\n- $ex")
                }
            }

            // get definition
            definition = ""
            definitions[0].definition?.let { def -> definition = def }

            return Term.EmptyTerm.copy(
                term = value,
                transcription = transcription,
                example = example,
                definition = definition
            )
        } else throw Exception(msg(R.string.error_un_known_error))
    }

    private fun msg(id: Int): String {
        return context.getString(id)
    }
}