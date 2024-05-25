package com.example.myactivity.data.repository

import com.example.myactivity.data.network.ApiService
import com.example.myactivity.data.model.User

class UserRepository(private val apiService: ApiService) {

    suspend fun login(email: String, password: String): Boolean {
        return apiService.login(email, password)
    }

    suspend fun register(user: User): Boolean {
        return apiService.register(user)
    }

    suspend fun naverLogin(accessToken: String): Boolean {
        return apiService.naverLogin(accessToken)
    }
}