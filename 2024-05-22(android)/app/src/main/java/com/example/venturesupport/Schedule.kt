package com.example.venturesupport

data class Schedule(
    val id: Int,
    val shipmentDate: String,
    val clientName: String,
    val clientNumber: String,
    val clientLocation: String,
    val productName: String,
    val productPrice: String,
    val productQuantity: String,
    val productTotalPrice: String,
    val shipmentSalary: String
)
