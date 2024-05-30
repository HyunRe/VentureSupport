package com.example.venturesupport


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApi {

    @GET("/api/payments")
    fun getAllPayments(): Call<List<Payment>>

    @GET("/api/payments/{id}")
    fun getPaymentById(@Path("id") id: Int): Call<Payment>

    @POST("/api/payments")
    fun createPayment(@Body payment: Payment): Call<Payment>

}
