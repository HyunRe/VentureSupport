package com.example.test2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApi {

    @GET("/api/products")
    fun getAllProducts(): Call<List<Product>>

    @GET("/api/products/{id}")
    fun getProductById(@Path("id") id: Int): Call<Product>

    @POST("/api/products")
    fun createProduct(@Body product: Product): Call<Product>

    @PUT("/api/products/{id}")
    fun updateProduct(@Path("id") id: Int, @Body product: Product): Call<Product>

    @DELETE("/api/products/{id}")
    fun deleteProduct(@Path("id") id: Int): Call<Void>
}