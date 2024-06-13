package com.example.venturesupport
import User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// AuthApi 인터페이스: 인증 관련 API 호출을 정의
interface AuthApi {
    // 사용자 등록 API 호출
    @POST("auth/users/register")
    fun registerUser(@Body user: User): Call<ResponseBody>

    // 사용자 로그인 API 호출
    @POST("auth/users/login")
    fun loginUser(@Body user: User): Call<ResponseBody>

    // 회사 등록 API 호출
    @POST("auth/companies/register")
    fun registerCompany(@Body company: Company): Call<ResponseBody>

    // 회사 로그인 API 호출
    @POST("auth/companies/login")
    fun loginCompany(@Body company: Company): Call<ResponseBody>
}
