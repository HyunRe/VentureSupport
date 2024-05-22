package com.example.myactivity.data.model


open class Order(
    val orderId: Int,
    val customerName: String,
    val address: String,
    val products: List<Product>// Assuming Product is another data class
)