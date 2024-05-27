package com.example.logapp2.data.model

import androidx.constraintlayout.compose.GeneratedValue
import androidx.room.Entity

data class Order(
    val id: Long,
    val customerName: String,
    val address: Address,
    val shipper: Shipper,
    val products: List<Product>// Assuming Product is another data class
)