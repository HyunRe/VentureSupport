package com.example.myactivity.data

data class SignUpData(
    val username: String,
    val email: String,
    val password: String,
    val navId: String,
    val userType: String
)
data class SignUpResponse(
    val success: Boolean,
    val message: String
)