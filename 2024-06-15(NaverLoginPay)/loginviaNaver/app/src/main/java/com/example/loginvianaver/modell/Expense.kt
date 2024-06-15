package com.example.loginvianaver.modell

data class Expense(
    val expenseId: Int?,
    val payment: Payment,
    val user: User,
    val expenseDetails: String,
    val expenseAmount: Double,
    val expenseDate: String
)