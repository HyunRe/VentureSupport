package com.example.myactivity.data.model

//총수입

import java.time.LocalDate

data class Income(
    val user: List<User>, //사용
    val shipmentId: Long, //사용
    val amount: Double, //confirm, 즉 접수된 원장에 대해 계산 필요
    val date: LocalDate?
)
