package com.example.myactivity.data.service

import com.example.myactivity.data.model.ProductInformation
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductInfoService {

    @GET("/api/product-information")
    fun getAllProductInformation(): Call<List<ProductInformation>>

    @GET("/api/product-information/{orderId}/{productId}/{userId}")
    fun getProductInformationById(@Path("orderId") orderId: Int, @Path("productId") productId: Int, @Path("userId") userId: Int): Call<ProductInformation>

    @POST("/api/product-information")
    fun createProductInformation(@Body productInformation: ProductInformation): Call<ProductInformation>

    @PUT("/api/product-information")
    fun updateProductInformation(@Body productInformation: ProductInformation): Call<ProductInformation>

    @DELETE("/api/product-information/{orderId}/{productId}/{userId}")
    fun deleteProductInformation(@Path("orderId") orderId: Int, @Path("productId") productId: Int, @Path("userId") userId: Int): Call<Void>

}