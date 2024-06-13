package com.example.venturesupport

import User

data class VehicleInventory(
    val vehicleInventoryId: Int? = null,
    val user: User,
    val product: Product,
    val vehicleInventoryQuantity: String
)