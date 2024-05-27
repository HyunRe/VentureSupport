package com.example.myactivity.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance {

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Companion.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    companion object {
        private const val BASE_URL = "http://yourapiurl.com/"
    }
}