package com.example.logapp2.data.model


data class Address(
    val id: Long?,
    val street: String?,
    val city: String?,
    val state: String?,
    val zipcode: String?,
    val companyUserId: Long?,
    val order: Order?
)
