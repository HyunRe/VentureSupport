package com.example.myactivity.data.service

import com.example.myactivity.data.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService{
    // Authentication endpoints
    @POST("/api/auth/register")
    fun registerUser(@Body user: User): Call<String>

    @POST("api/navLogin")
    fun naverLogin(@Body accessToken: String): Response<Boolean>

    @POST("/api/auth/login")
    fun loginUser(@Body user: User): Call<String>
}