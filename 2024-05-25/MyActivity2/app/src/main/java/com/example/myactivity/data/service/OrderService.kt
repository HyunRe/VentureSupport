package com.example.myactivity.data.service

import com.example.myactivity.data.model.Order

interface OrderService {
    fun save(order: Order): Order
    fun findAll(): List<Order>
    fun findByUserId(userId: Long): List<Order>
}