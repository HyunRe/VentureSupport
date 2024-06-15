package com.example.loginvianaver.modell

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId") val productId: Int?,
    @SerializedName("productName") val productName: String?,
    @SerializedName("productPrice") val productPrice: Int?,
    @SerializedName("productQuantity") val productQuantity: String?
)