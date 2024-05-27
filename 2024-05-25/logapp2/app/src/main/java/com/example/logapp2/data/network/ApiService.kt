package com.example.logapp2.data.network

import com.example.logapp2.data.model.ApiResponse
import com.example.logapp2.data.model.Order
import com.example.logapp2.data.model.User
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("/orders")
    suspend fun getOrders(): List<Order>

    @POST("/order")
    suspend fun sendOrder(@Body order: Order): Boolean

    @GET("/order/{id}")
    suspend fun getOrderById(@Path("id") orderId: Int): Order

    @POST("/login")
    suspend fun login(@Body email: String, @Body password: String): Boolean

    @POST("users/register")
    suspend fun registerUser(@Body user: User): Response<ApiResponse>

    @POST("/navLogin")
    suspend fun naverLogin(@Body accessToken: String): Boolean
}
