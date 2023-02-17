package com.kappdev.wordbook.feature_dictionary.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val meaningApi: MeaningApi by lazy {
        Retrofit.Builder()
            .baseUrl(MeaningApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MeaningApi::class.java)
    }

}