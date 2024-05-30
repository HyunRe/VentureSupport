package com.example.test2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/users/register")
    fun registerUser(@Body user: User): Call<String>

    @POST("/api/auth/users/login")
    fun loginUser(@Body user: User): Call<String>

    @POST("/api/auth/companies/register")
    fun registerCompany(@Body company: Company): Call<String>

    @POST("/api/auth/companies/login")
    fun loginCompany(@Body company: Company): Call<String>
}
