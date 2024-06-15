package com.example.venturesupport

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SupplierApi {

    @GET("suppliers")
    fun getAllSuppliers(): Call<List<Supplier>>

    @GET("suppliers/{id}")
    fun getSupplierById(@Path("id") id: Int): Call<Supplier>

    @POST("suppliers")
    fun createSupplier(@Body supplier: Supplier): Call<Supplier>

    @PUT("suppliers/{id}")
    fun updateSupplier(@Path("id") id: Int, @Body supplier: Supplier): Call<Supplier>

    @DELETE("suppliers/{id}")
    fun deleteSupplier(@Path("id") id: Int): Call<Void>
}
