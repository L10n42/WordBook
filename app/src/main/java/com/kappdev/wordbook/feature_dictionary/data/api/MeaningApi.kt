package com.kappdev.wordbook.feature_dictionary.data.api

import com.kappdev.wordbook.feature_dictionary.domain.model.propose_meaning.MeaningModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MeaningApi {

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/"
        const val WORD_PAR = "word"
        const val END_POINT = "{$WORD_PAR}"
    }

    @GET(END_POINT)
    suspend fun getMeaning(@Path(WORD_PAR) word: String): MeaningModel

}