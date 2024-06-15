package com.example.loginvianaver.modell



data class Payment(
    val paymentId: Int? = null, // 결제 ID
    val user: User, // 결제를 한 사용자 정보
    val paymentName: String// 결제 방법 이름
)
