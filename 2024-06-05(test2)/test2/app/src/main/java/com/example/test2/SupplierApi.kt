package com.example.test2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit 인터페이스를 정의하여 공급자 관련 API 호출을 관리합니다.
 */
interface SupplierApi {

    /**
     * 모든 공급자 목록을 가져오는 GET 요청입니다.
     * @return Call<List<Supplier>> - 공급자 리스트를 반환하는 Call 객체
     */
    @GET("suppliers")
    fun getAllSuppliers(): Call<List<Supplier>>

    /**
     * 특정 ID의 공급자 정보를 가져오는 GET 요청입니다.
     * @param id Int - 공급자 ID
     * @return Call<Supplier> - 특정 공급자 정보를 반환하는 Call 객체
     */
    @GET("suppliers/{id}")
    fun getSupplierById(@Path("id") id: Int): Call<Supplier>

    /**
     * 새로운 공급자를 생성하는 POST 요청입니다.
     * @param supplier Supplier - 생성할 공급자 객체
     * @return Call<Supplier> - 생성된 공급자 정보를 반환하는 Call 객체
     */
    @POST("suppliers")
    fun createSupplier(@Body supplier: Supplier): Call<Supplier>

    /**
     * 특정 ID의 공급자 정보를 업데이트하는 PUT 요청입니다.
     * @param id Int - 업데이트할 공급자의 ID
     * @param supplier Supplier - 업데이트할 공급자 객체
     * @return Call<Supplier> - 업데이트된 공급자 정보를 반환하는 Call 객체
     */
    @PUT("suppliers/{id}")
    fun updateSupplier(@Path("id") id: Int, @Body supplier: Supplier): Call<Supplier>

    /**
     * 특정 ID의 공급자를 삭제하는 DELETE 요청입니다.
     * @param id Int - 삭제할 공급자의 ID
     * @return Call<Void> - 삭제 요청의 결과를 반환하는 Call 객체
     */
    @DELETE("suppliers/{id}")
    fun deleteSupplier(@Path("id") id: Int): Call<Void>
}
