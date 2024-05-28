package com.example.app.model
//주소 클래스

data class Address(
    val id: Long,
    val street: String?,
    val city: String?,
    val state: String?,
    val zipcode: String?,
    val companyUserId: Long?,
    val order: Order?
)
