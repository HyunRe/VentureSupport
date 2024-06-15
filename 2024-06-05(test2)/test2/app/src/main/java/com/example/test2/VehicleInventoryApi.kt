package com.example.test2

import retrofit2.Call
import retrofit2.http.*

/**
 * Retrofit 인터페이스를 정의하여 차량 재고 관련 API 호출을 관리합니다.
 */
interface VehicleInventoryApi {

    /**
     * 모든 차량 재고 목록을 가져오는 GET 요청입니다.
     * @return Call<List<VehicleInventory>> - 차량 재고 리스트를 반환하는 Call 객체
     */
    @GET("vehicle-inventories")
    fun getAllVehicleInventories(): Call<List<VehicleInventory>>

    /**
     * 특정 ID의 차량 재고 정보를 가져오는 GET 요청입니다.
     * @param id Int - 차량 재고 ID
     * @return Call<VehicleInventory> - 특정 차량 재고 정보를 반환하는 Call 객체
     */
    @GET("vehicle-inventories/{id}")
    fun getVehicleInventoryById(@Path("id") id: Int): Call<VehicleInventory>

    /**
     * 특정 사용자 ID에 해당하는 차량 재고 목록을 가져오는 GET 요청입니다.
     * @param userId Int - 사용자 ID
     * @return Call<List<VehicleInventory>> - 사용자의 차량 재고 리스트를 반환하는 Call 객체
     */
    @GET("vehicle-inventories/users/{id}")
    fun getVehicleInventoriesByUserId(@Path("id") userId: Int): Call<List<VehicleInventory>>

    /**
     * 특정 제품 ID에 해당하는 차량 재고 목록을 가져오는 GET 요청입니다.
     * @param productId Int - 제품 ID
     * @return Call<List<VehicleInventory>> - 제품의 차량 재고 리스트를 반환하는 Call 객체
     */
    @GET("vehicle-inventories/products/{id}")
    fun getVehicleInventoriesByProductId(@Path("id") productId: Int): Call<List<VehicleInventory>>

    /**
     * 새로운 차량 재고를 생성하는 POST 요청입니다.
     * @param vehicleInventory VehicleInventory - 생성할 차량 재고 객체
     * @return Call<VehicleInventory> - 생성된 차량 재고 정보를 반환하는 Call 객체
     */
    @POST("vehicle-inventories")
    fun createVehicleInventory(@Body vehicleInventory: VehicleInventory): Call<VehicleInventory>

    /**
     * 특정 ID의 차량 재고 정보를 업데이트하는 PUT 요청입니다.
     * @param id Int - 업데이트할 차량 재고의 ID
     * @param vehicleInventory VehicleInventory - 업데이트할 차량 재고 객체
     * @return Call<VehicleInventory> - 업데이트된 차량 재고 정보를 반환하는 Call 객체
     */
    @PUT("vehicle-inventories/{id}")
    fun updateVehicleInventory(@Path("id") id: Int, @Body vehicleInventory: VehicleInventory): Call<VehicleInventory>

    /**
     * 특정 ID의 차량 재고를 삭제하는 DELETE 요청입니다.
     * @param id Int - 삭제할 차량 재고의 ID
     * @return Call<Void> - 삭제 요청의 결과를 반환하는 Call 객체
     */
    @DELETE("vehicle-inventories/{id}")
    fun deleteVehicleInventory(@Path("id") id: Int): Call<Void>
}
