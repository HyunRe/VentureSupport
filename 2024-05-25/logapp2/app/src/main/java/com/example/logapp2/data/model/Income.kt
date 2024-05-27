package com.example.logapp2.data.model


import java.time.LocalDate

data class Income(
    val user: User?,
    val shipmentId: Long,
    val amount: Double,
    val date: LocalDate?
)
