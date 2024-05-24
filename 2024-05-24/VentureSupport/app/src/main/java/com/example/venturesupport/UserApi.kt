package com.example.venturesupport

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET("users/name/")
    fun getUserByUserName(@Query("name") userName: String): Call<User>

    @GET("users/email/")
    fun getUserByEmail(@Query("email") email: String): Call<User>

    @GET("users/id/")
    fun getUserById(@Query("id") id: String): Call<User>

    @POST("users/update")
    fun updateUser(
        @Query("email") email: String,
        @Query("id") id: String,
        @Query("name") name: String,
        @Query("phone") phone: String,
        @Query("password") password: String
    ): Call<User>
}
