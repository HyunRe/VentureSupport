package com.example.myactivity.data.model
//유저 정보
// User.kt
data class User(
    val username: String?,
    val password: String?,
    val email: String?,
    val latitude: Double, //위도
    val longitude: Double, //경도
    val navId: String? = null
)

// ApiResponse.kt
data class ApiResponse(
    val success: Boolean,
    val userType: String?,
    val message: String?
)
