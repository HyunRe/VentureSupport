package com.example.test2

import User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    fun loginUser(@Body user : User): Call<ResponseBody>

    @POST("/auth/register")
    fun registerUser(@Body user : User): Call<ResponseBody>
}
