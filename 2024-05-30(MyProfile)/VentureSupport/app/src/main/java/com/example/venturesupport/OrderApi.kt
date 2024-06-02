package com.example.venturesupport

import retrofit2.Call
import retrofit2.http.*

interface OrderApi {
    @GET("/orders")
    fun getAllOrders(): Call<List<Order>>

    @GET("orders/users/{id}")
    fun getOrderByUserId(@Path("id") id: Int): Call<List<Order>>
}
