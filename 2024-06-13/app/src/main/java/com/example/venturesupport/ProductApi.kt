package com.example.venturesupport

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApi {

    /**
     * 모든 제품 정보를 가져오는 GET 요청.
     */
    @GET("products")
    fun getAllProducts(): Call<List<Product>>

    /**
     * 특정 ID를 가진 제품 정보를 가져오는 GET 요청.
     * @param id 제품 ID
     */
    @GET("products/{id}")
    fun getProductById(@Path("id") id: Int): Call<Product>

    /**
     * 특정 공급자 ID를 가진 제품 목록을 가져오는 GET 요청.
     * @param id 공급자 ID
     */
    @GET("products/suppliers/{id}")
    fun getProductsByCompanyId(@Path("id") id: Int): Call<List<Product>>

    /**
     * 새로운 제품 정보를 생성하는 POST 요청.
     * @param product 생성할 제품 정보
     */
    @POST("products")
    fun createProduct(@Body product: Product): Call<Product>

    /**
     * 특정 ID를 가진 제품 정보를 업데이트하는 PUT 요청.
     * @param id 제품 ID
     * @param product 업데이트할 제품 정보
     */
    @PUT("products/{id}")
    fun updateProduct(@Path("id") id: Int, @Body product: Product): Call<Product>

    /**
     * 특정 ID를 가진 제품을 삭제하는 DELETE 요청.
     * @param id 삭제할 제품 ID
     */
    @DELETE("products/{id}")
    fun deleteProduct(@Path("id") id: Int): Call<Void>
}
