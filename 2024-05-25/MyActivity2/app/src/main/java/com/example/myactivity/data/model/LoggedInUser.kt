package com.example.myactivity.data.model
//로그인 유저 구분 클래스
/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
class LoggedInUser(val id: String, @JvmField val displayName: String)