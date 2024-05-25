package com.example.myactivity.data.model

data class User(
    val userId: String,
    val email: String,
    val userName: String,
    val password: String,
    val kakaoUserId: String? = null
)