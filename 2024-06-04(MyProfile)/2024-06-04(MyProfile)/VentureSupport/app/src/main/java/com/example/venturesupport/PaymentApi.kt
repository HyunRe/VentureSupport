package com.example.venturesupport

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// 결제 관련 API 정의
interface PaymentApi {

    // 모든 결제 수단 가져오기
    @GET("/api/payments")
    fun getAllPayments(): Call<List<Payment>>

    // ID에 해당하는 결제 수단 가져오기
    @GET("/api/payments/{id}")
    fun getPaymentById(@Path("id") id: Int): Call<Payment>

    // 새로운 결제 수단 생성
    @POST("/api/payments")
    fun createPayment(@Body payment: Payment): Call<Payment>
}
