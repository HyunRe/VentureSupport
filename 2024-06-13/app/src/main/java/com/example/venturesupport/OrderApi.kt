package com.example.venturesupport

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderApi {
    @GET("orders") //모든 주문정보 가져오기
    fun getAllOrders(): Call<List<Order>>

    @GET("orders/{id}") //해당하는 주문 ID의 내용 가져오기
    fun getOrderById(@Path("id") id: Int): Call<Order>

    @GET("orders/users/{id}") //해당하는 유저 ID 내의 모든 주문정보 가져오기
    fun getOrdersByUserId(@Path("id") id: Int): Call<List<Order>>

    @GET("orders/suppliers/{id}") //해당하는 공급자 ID 내의 모든 주문정보 가져오기
    fun getOrdersByCompanyId(@Path("id") id: Int): Call<List<Order>>

    @POST("orders") //주문 입력하기
    fun createOrder(@Body order: Order): Call<Order>

    @PUT("orders/{id}") //해당하는 주문 ID의 내용 갱신하기
    fun updateOrder(@Path("id") id: Int, @Body order: Order): Call<Order>

    @DELETE("orders/{id}") //해당하는 주문 ID의 내용 지우기
    fun deleteOrder(@Path("id") id: Int): Call<Void>
}
