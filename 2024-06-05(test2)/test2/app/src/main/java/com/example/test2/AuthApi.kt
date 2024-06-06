package com.example.test2

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/users/register")
    fun registerUser(@Body user: User): Call<ResponseBody>

    @POST("auth/users/login")
    fun loginUser(@Body user: User): Call<ResponseBody>

    @POST("auth/companies/register")
    fun registerCompany(@Body company: Company): Call<ResponseBody>

    @POST("auth/companies/login")
    fun loginCompany(@Body company: Company): Call<ResponseBody>
}
