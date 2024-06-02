package com.example.venturesupport

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// ExpenseApi 인터페이스: REST API를 통해 지출 정보를 관리하기 위한 인터페이스
interface ExpenseApi {

        // 모든 지출 정보를 가져오는 GET 요청
        @GET("/api/expenses")
        fun getAllExpenses(): Call<List<Expense>>

        // 특정 유저 ID의 지출 정보를 가져오는 GET 요청
        @GET("/api/expenses/{id}")
        fun getExpenseById(@Path("id") id: Int): Call<Expense>

        // 새로운 지출 정보를 생성하는 POST 요청
        @POST("/api/expenses")
        fun createExpense(@Body expense: Expense): Call<Expense>

        // 특정 ID의 지출 정보를 업데이트하는 PUT 요청
        @PUT("/api/expenses/{id}")
        fun updateExpense(@Path("id") id: Int, @Body expense: Expense): Call<Expense>

        // 특정 ID의 지출 정보를 삭제하는 DELETE 요청
        @DELETE("/api/expenses/{id}")
        fun deleteExpense(@Path("id") id: Int): Call<Void>
}
