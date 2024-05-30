package com.example.venturesupport
import User
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Order(
    @SerializedName("orderId") val orderId: Int?,
    @SerializedName("date") val date: Date,
    @SerializedName("supplierName") val supplierName: String,
    @SerializedName("supplierPhoneNumber") val supplierPhoneNumber: String,
    @SerializedName("supplierLocation") val supplierLocation: String,
    @SerializedName("salary") val salary: Double,
    @SerializedName("totalAmount") val totalAmount: Int,
    @SerializedName("user") val user: User
)
