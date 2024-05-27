package com.example.myactivity.data.model

data class Product(
    val id: Long,
    val name: String,
    val price: Double,
    val quantity: Int,
    val companyUserId: Long,
)