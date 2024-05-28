package com.example.logapp2.data.network

import com.example.logapp2.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/users/login")
    fun loginUser(@Body user: User): Call<String>
}
