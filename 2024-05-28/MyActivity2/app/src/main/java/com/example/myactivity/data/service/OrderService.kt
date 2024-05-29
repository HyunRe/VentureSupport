package com.example.myactivity.data.service

import com.example.myactivity.data.model.Order
import com.example.myactivity.data.model.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderService {
    // Order-related endpoints
    @GET("/api/orders")
    fun getAllOrders(): Call<List<Order>>

    @GET("/api/orders/{id}")
    fun getOrderById(@Path("id") id: Int): Call<Order>

    @POST("/api/orders")
    fun createOrder(@Body order: Order): Call<Order>

    @PUT("/api/orders/{id}")
    fun updateOrder(@Path("id") id: Int, @Body order: Order): Call<Order>

    @DELETE("/api/orders/{id}")
    fun deleteOrder(@Path("id") id: Int): Call<Void>

    @POST("/orders/{orderId}/products")
    suspend fun addProductToOrder(
        @Path("orderId") orderId: Long,
        @Body product: Product
    ): Response<Order>
}