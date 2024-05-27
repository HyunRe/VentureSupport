package com.example.myactivity.data.model
//주문 정보

data class Order(
    val id: Long,
    val customerName: String,
    val address: List<Address>,
    val products: List<Product>,// Assuming Product is another data class
    val shipper: List<Shipper>,

)