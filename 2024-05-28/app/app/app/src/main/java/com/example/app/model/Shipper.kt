package com.example.app.model
//운송자 정보
data class Shipper(
    val id: Long,
    val name: String?,
    val phone: String?,
    val available: Boolean
)