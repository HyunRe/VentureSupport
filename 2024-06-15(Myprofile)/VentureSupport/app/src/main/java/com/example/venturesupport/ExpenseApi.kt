package com.example.venturesupport

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ExpenseApi {
        @GET("expenses")
        fun getAllExpenses(): Call<List<Expense>>

        @GET("expenses/{id}")
        fun getExpenseById(@Path("id") id: Int): Call<Expense>

        @GET("expenses/users/{id}")
        fun getExpensesByUserId(@Path("id") id: Int): Call<List<Expense>>

        @POST("expenses")
        fun createExpense(@Body expense: Expense): Call<Expense>

        @PUT("expenses/{id}")
        fun updateExpense(@Path("id") id: Int, @Body expense: Expense): Call<Expense>

        @DELETE("expenses/{id}")
        fun deleteExpense(@Path("id") id: Int): Call<Void>
}
