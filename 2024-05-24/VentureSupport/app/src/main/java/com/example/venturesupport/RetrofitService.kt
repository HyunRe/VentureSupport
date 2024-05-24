package com.example.venturesupport

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "http://223.194.134.136:8080/users/6/" // http://localhost:8080/login

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userService: UserApi by lazy {
        instance.create(UserApi::class.java)
    }
}