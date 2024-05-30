package com.example.venturesupport

import Order
import retrofit2.Call
import retrofit2.http.*

interface OrderApi {

    @GET("orders/users/{id}")
    fun getOrderByUserId(@Path("id") id: Int): Call<List<Order>>

    @POST("orders")
    fun createOrder(@Body order: Order): Call<Order>
}
