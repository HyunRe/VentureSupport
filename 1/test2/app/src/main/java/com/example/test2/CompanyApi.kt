package com.example.test2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CompanyApi {

    @GET("companies")
    fun getAllCompanies(): Call<List<Company>>

    @GET("companies/{id}")
    fun getCompanyById(@Path("id") id: Int): Call<Company>

    @POST("companies")
    fun createCompany(@Body company: Company): Call<Company>

    @PUT("companies/{id}")
    fun updateCompany(@Path("id") id: Int, @Body company: Company): Call<Company>

    @DELETE("companies/{id}")
    fun deleteCompany(@Path("id") id: Int): Call<Void>
}