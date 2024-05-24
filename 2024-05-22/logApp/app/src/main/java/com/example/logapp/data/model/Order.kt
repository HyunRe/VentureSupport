package com.example.logapp.data.model

data class Order(
    val orderId: Int,
    val customerName: String,
    val address: String,
    val products: List<Product>// Assuming Product is another data class
)