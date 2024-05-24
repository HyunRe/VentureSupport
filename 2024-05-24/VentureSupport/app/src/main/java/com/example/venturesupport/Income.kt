package com.example.venturesupport

import java.util.Calendar

data class Income (
    val shipment_id: Int,
    val user_id: String,
    val shipment_salary: Int,
    val shipment_date: Calendar,
    val shipment_amount: Int
)