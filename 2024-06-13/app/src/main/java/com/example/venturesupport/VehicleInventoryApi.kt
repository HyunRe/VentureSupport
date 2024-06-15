package com.example.venturesupport
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VehicleInventoryApi {

    @GET("vehicle-inventories")
    fun getAllVehicleInventories(): Call<List<VehicleInventory>>

    @GET("vehicle-inventories/{id}")
    fun getVehicleInventoryById(@Path("id") id: Int): Call<VehicleInventory>

    @GET("vehicle-inventories/users/{id}")
    fun getVehicleInventoriesByUserId(@Path("id") userId: Int): Call<List<VehicleInventory>>

    @GET("vehicle-inventories/products/{id}")
    fun getVehicleInventoriesByProductId(@Path("id") productId: Int): Call<List<VehicleInventory>>

    @POST("vehicle-inventories")
    fun createVehicleInventory(@Body vehicleInventory: VehicleInventory): Call<VehicleInventory>

    @PUT("vehicle-inventories/{id}")
    fun updateVehicleInventory(@Path("id") id: Int, @Body vehicleInventory: VehicleInventory): Call<VehicleInventory>

    @DELETE("vehicle-inventories/{id}")
    fun deleteVehicleInventory(@Path("id") id: Int): Call<Void>
}
