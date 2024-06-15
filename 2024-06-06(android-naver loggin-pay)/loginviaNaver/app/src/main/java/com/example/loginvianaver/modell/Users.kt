package com.example.loginvianaver.modell

/**
* 사용자를 나타내는 데이터 클래스입니다.
* @property userId 사용자 ID입니다.
* @property username 사용자 이름입니다.
* @property email 사용자 이메일 주소입니다.
* @property lat 사용자의 위도 좌표입니다.
* @property lng 사용자의 경도 좌표입니다.
* @property phone 사용자 전화번호입니다.
* @property role 사용자의 역할(UserRole 열거형)입니다.
* @property password 사용자 비밀번호입니다.
*/
data class User(
    val id: Int?,
    val username: String?,
    val email: String?,
    val lat: Double?,
    val lng: Double?,
    val phone: String?,
    val password: String?,
    val inventoryQuantity : String?
)