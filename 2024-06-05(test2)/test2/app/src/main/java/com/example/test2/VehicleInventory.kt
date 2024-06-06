package com.example.test2

import Product

data class VehicleInventory(
    val vehicleInventoryId: Int? = null,
    val user: User,
    val product: Product,
    val vehicleInventoryQuantity: String
)