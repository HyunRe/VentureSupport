package com.example.myactivity.data.service
import com.example.myactivity.data.model.User
import com.example.myactivity.data.model.Warehouse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface WarehouseService {
    @GET("/api/warehouses")
    fun getAllWarehouses(): Call<List<Warehouse>>

    @GET("/api/warehouses/{id}")
    fun getWarehouseById(@Path("id") id: Int): Call<Warehouse>

   //검증 필요
    @GET("/api/users/{id}")
    fun getWarehousesByUserId(@Path("user_id") userId: User): Call<Warehouse>
//리포리지토리 사용 필
    @POST("/api/warehouses")
    fun createWarehouse(@Body warehouse: Warehouse): Call<Warehouse>

    @PUT("/api/warehouses/{id}")
    fun updateWarehouse(@Path("id") id: Int, @Body warehouse: Warehouse): Call<Warehouse>

    @DELETE("/api/warehouses/{id}")
    fun deleteWarehouse(@Path("id") id: Int): Call<Void>
}