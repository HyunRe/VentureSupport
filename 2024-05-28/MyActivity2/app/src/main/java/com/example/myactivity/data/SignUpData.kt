package com.example.myactivity.data

data class SignUpData(
    val username: String?,
    val password: String?,
    val email: String?,
    val latitude: Double = 10.1234,
    val longitude: Double = 20.5678,
    val navId: String? = null,
    val roll: String
    )
data class SignUpResponse(
    val success: Boolean,
    val message: String,
    val roll: String
) {
    companion object {
        // 여기에 필요한 메서드를 추가할 수 있습니다.
    }
}
