package com.example.logapp2.data

data class User(
    val userId: String,
    val email: String,
    val userName: String,
    val password: String,
    val navId: String? = null
)