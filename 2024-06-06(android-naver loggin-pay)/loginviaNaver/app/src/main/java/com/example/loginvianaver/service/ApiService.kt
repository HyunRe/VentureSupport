package com.example.loginvianaver.service

import com.example.loginvianaver.modell.Payment
import com.example.loginvianaver.modell.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// Retrofit 인터페이스 정의
interface ApiService {

    // 사용자 등록을 위한 POST 요청. 요청 본문에는 User 객체가 포함됨.
    @POST("/api/auth/register")
    fun registerUser(@Body user: User): Call<String>

    // 사용자 로그인 요청을 위한 POST. 요청 본문에는 User 객체가 포함됨.
    @POST("/api/auth/login")
    fun loginUser(@Body user: User): Call<String>

    // 모든 사용자 정보를 조회하기 위한 GET 요청.
    @GET("/api/users")
    fun getAllUsers(): Call<List<User>>

    // 특정 사용자의 정보를 업데이트하기 위한 PUT 요청. {id}는 사용자 ID를 나타냄.
    @PUT("/users/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: User): Call<User>

    // 새 사용자를 생성하기 위한 POST 요청. 요청 본문에는 User 객체가 포함됨.
    @POST("/api/users")
    fun createUser(@Body user: User): Call<User>

    // 결제 정보를 생성하기 위한 POST 요청. 요청 본문에는 Payment 객체가 포함됨.
    @POST("api/payments")
    fun createPayment(@Body payment: Payment): Call<Payment>
}
