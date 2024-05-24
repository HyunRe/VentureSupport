package com.example.venturesupport

import java.util.Date

data class Expense (
    val expense_id: Int,
    val user_id: String,
    val expense_record: String,
    val expense_date: Date
)