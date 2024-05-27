package com.example.myactivity.data.model

// User.kt
data class User(
    val username: String?,
    val password: String?,
    val email: String?,
    val latitude: Double = 10.1234,
    val longitude: Double = 20.5678,
    val navId: String? = null
)

// ApiResponse.kt
data class ApiResponse(
    val success: Boolean,
    val userType: String?,
    val message: String?
)
