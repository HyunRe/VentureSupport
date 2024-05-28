package com.example.logapp2.data.model

data class Product(
    val id: Long,
    val name: String,
    val price: Double,
    val quantity: Int,
    val companyUserId: Long,
)