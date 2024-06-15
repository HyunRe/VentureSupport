package com.example.loginvianaver.modell

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Expense(
    val expenseId: Int?, // 지출 ID
    val payment: Payment,// 결제 정보
    val user: User, // 사용자 정보
    val expenseDetails: String,// 지출 세부 사항
    val expenseAmount: Double,// 지출 금액
    val expenseDate: Date// 지출 날짜
)