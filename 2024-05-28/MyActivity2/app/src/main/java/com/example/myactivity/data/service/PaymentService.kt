package com.example.myactivity.data.service

import com.example.myactivity.data.model.Payment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PaymentService {
    // Payment API Endpoints
    @GET("/api/payments")
    fun getAllPayments(): Call<List<Payment>>

    @GET("/api/payments/{id}")
    fun getPaymentById(@Path("id") id: Int): Call<Payment>

    @POST("/api/payments")
    fun createPayment(@Body payment: Payment): Call<Payment>

    @PUT("/api/payments/{id}")
    fun updatePayment(@Path("id") id: Int, @Body payment: Payment): Call<Payment>

    @DELETE("/api/payments/{id}")
    fun deletePayment(@Path("id") id: Int): Call<Void>
}