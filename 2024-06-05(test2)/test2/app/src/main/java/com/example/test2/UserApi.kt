package com.example.test2

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: User): Call<ResponseBody>

    @GET("users") //모든 유저 정보 획득
    fun getAllUsers(): Call<List<User>>
}
