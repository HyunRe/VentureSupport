package com.example.app.service

import com.example.app.model.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface authService {

    //회원가입
    @POST("/api/auth/register")
    fun registerUser(@Body user: User): Call<String>

    //로그인
    @POST("/api/auth/login")
    fun loginUser(@Body user: User): Call<String>
    //네이버 로그인

}
