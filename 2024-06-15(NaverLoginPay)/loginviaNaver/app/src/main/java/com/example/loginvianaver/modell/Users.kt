package com.example.loginvianaver.modell

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId") val userId: Int?,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String,
    @SerializedName("userPhoneNumber") val userPhoneNumber: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lng") val lng: Double?,
    @SerializedName("userPassword") val userPassword: String
)