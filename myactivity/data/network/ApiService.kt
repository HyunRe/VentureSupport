package com.example.myactivity.data.network

import com.example.myactivity.data.model.Order
import com.example.myactivity.data.model.User

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class ApiService {
    interface ApiService {

        @GET("/orders")
        suspend fun getOrders(): List<Order>

        @POST("/order")
        suspend fun sendOrder(@Body order: Order): Boolean

        @GET("/order/{id}")
        suspend fun getOrderById(@Path("id") orderId: Int): Order

        @POST("/login")
        suspend fun login(@Body email: String, @Body password: String): Boolean

        @POST("/register")
        suspend fun register(@Body user: User): Boolean

        @POST("/naverLogin")
        suspend fun naverLogin(@Body accessToken: String): Boolean
    }
}