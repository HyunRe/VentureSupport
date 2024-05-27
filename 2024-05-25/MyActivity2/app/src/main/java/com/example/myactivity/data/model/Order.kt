package com.example.myactivity.data.model


data class Order(
    val id: Long,
    val customerName: String,
    val address: Address,
    val shipper: Shipper,
    val products: List<Product>// Assuming Product is another data class
)