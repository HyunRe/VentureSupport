package com.example.myactivity.data.service

import com.example.myactivity.data.model.VehicleInventory
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VehicleService {
    @GET("/api/vehicle-inventory")
    fun getAllVehicleInventory(): Call<List<VehicleInventory>>

    @GET("/api/vehicle-inventory/{id}")
    fun getVehicleInventoryById(@Path("id") id: Int): Call<VehicleInventory>

    @POST("/api/vehicle-inventory")
    fun createVehicleInventory(@Body vehicleInventory: VehicleInventory): Call<VehicleInventory>

    @PUT("/api/vehicle-inventory/{id}")
    fun updateVehicleInventory(@Path("id") id: Int, @Body vehicleInventory: VehicleInventory): Call<VehicleInventory>

    @DELETE("/api/vehicle-inventory/{id}")
    fun deleteVehicleInventory(@Path("id") id: Int): Call<Void>
}