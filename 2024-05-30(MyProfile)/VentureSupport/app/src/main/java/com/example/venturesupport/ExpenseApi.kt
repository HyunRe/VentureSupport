package com.example.venturesupport

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ExpenseApi{
        @GET("/api/expenses")
        fun getAllExpenses(): Call<List<Expense>>

        @GET("/api/expenses/{id}")
        fun getExpenseById(@Path("id") id: Int): Call<Expense>

        @POST("/api/expenses")
        fun createExpense(@Body expense: Expense): Call<Expense>

        @PUT("/api/expenses/{id}")
        fun updateExpense(@Path("id") id: Int, @Body expense: Expense): Call<Expense>

        @DELETE("/api/expenses/{id}")
        fun deleteExpense(@Path("id") id: Int): Call<Void>
    }
