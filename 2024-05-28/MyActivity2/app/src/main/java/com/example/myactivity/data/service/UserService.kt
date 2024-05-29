package com.example.myactivity.data.service

import com.example.myactivity.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    // user endpoints
    @GET("/api/users")
    fun getAllUsers(): Call<List<User>>

    @GET("/api/users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @POST("/api/users")
    fun createUser(@Body user: User): Call<User>

    @PUT("/api/users/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: User): Call<User>

    @DELETE("/api/users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Void>
}