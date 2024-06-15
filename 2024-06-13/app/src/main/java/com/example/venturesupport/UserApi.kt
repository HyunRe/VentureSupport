package com.example.venturesupport

import User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @GET("users") //모든 유저 정보 일괄반환
    fun getAllUsers(): Call<List<User>>

    @GET("users/{id}") //일련번호에 해당하는 유저정보 획득
    fun getUserById(@Path("id") id: Int): Call<User>

    @PUT("users/{id}") //일련번호에 해당하는 유저 정보 갱신
    fun updateUser(@Path("id") id: Int, @Body user: User): Call<ResponseBody>

    @POST("users") //신규 유저 등록
    fun createUser(@Body user: User): Call<User>
}
