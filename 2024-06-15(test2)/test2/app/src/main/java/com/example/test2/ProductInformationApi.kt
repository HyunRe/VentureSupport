package com.example.test2

import Product
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductInformationApi {
    @GET("product-information")
    fun getAllProductInformation(): Call<List<ProductInformation>>

    @GET("product-information/{id}")
    fun getProductInformationById(@Path("id") id: Int): Call<ProductInformation>

    @GET("product-information/orders/{id}/products")
    fun getProductInformationByOrderId(@Path("id") orderId: Int): Call<List<Product>>

    @POST("product-information")
    fun createProductInformation(@Body productInformation: ProductInformation): Call<ProductInformation>

    @PUT("product-information/{id}")
    fun updateProductInformation(@Path("id") id: Int, @Body productInformation: ProductInformation): Call<ProductInformation>

    @DELETE("product-information/{id}")
    fun deleteProductInformation(@Path("id") id: Int): Call<Void>

    @DELETE("product-information/orders/{id}")
    fun deleteProductInformationByOrderId(@Path("id") id: Int): Call<Void>
}