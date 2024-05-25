package com.example.myactivity.data.network
import com.example.myactivity.data.network.ApiService

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


class RetrofitInstance {
    private const val BASE_URL = "http://yourapiurl.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}