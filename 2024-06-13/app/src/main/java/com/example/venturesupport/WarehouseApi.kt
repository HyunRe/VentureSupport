package com.example.venturesupport

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * 창고 관련 API를 정의하는 인터페이스입니다.
 */
interface WarehouseApi {

    /**
     * 새로운 창고를 생성하는 API 요청을 정의합니다.
     * @param warehouse Warehouse - 생성할 창고 정보
     * @return Call<Warehouse> - API 호출 결과를 담은 Call 객체
     */
    @POST("warehouses")
    fun createWarehouse(@Body warehouse: Warehouse): Call<Warehouse>

    /**
     * 특정 사용자의 창고 목록을 가져오는 API 요청을 정의합니다.
     * @param id Int - 사용자 ID
     * @return Call<List<Warehouse>> - API 호출 결과를 담은 Call 객체
     */
    @GET("warehouses/users/{id}")
    fun getWarehousesByUserId(@Path("id") id: Int): Call<List<Warehouse>>

    /**
     * 특정 창고를 삭제하는 API 요청을 정의합니다.
     * @param id Int - 삭제할 창고의 ID
     * @return Call<Void> - API 호출 결과를 담은 Call 객체
     */
    @DELETE("warehouses/{id}")
    fun deleteWarehouse(@Path("id") id: Int): Call<Void>
}
