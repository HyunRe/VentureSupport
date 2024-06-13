package com.example.venturesupport

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * 회사 관련 API를 정의하는 인터페이스입니다.
 */
interface CompanyApi {

    /**
     * 모든 회사를 조회하는 API 요청을 정의합니다.
     * @return Call<List<Company>> - API 호출 결과를 담은 Call 객체
     */
    @GET("companies")
    fun getAllCompanies(): Call<List<Company>>

    /**
     * 특정 회사 정보를 조회하는 API 요청을 정의합니다.
     * @param id Int - 조회할 회사의 ID
     * @return Call<Company> - API 호출 결과를 담은 Call 객체
     */
    @GET("companies/{id}")
    fun getCompanyById(@Path("id") id: Int): Call<Company>

    /**
     * 새로운 회사를 생성하는 API 요청을 정의합니다.
     * @param company Company - 생성할 회사 정보
     * @return Call<Company> - API 호출 결과를 담은 Call 객체
     */
    @POST("companies")
    fun createCompany(@Body company: Company): Call<Company>

    /**
     * 특정 회사 정보를 수정하는 API 요청을 정의합니다.
     * @param id Int - 수정할 회사의 ID
     * @param company Company - 수정할 회사 정보
     * @return Call<Company> - API 호출 결과를 담은 Call 객체
     */
    @PUT("companies/{id}")
    fun updateCompany(@Path("id") id: Int, @Body company: Company): Call<Company>

    /**
     * 특정 회사를 삭제하는 API 요청을 정의합니다.
     * @param id Int - 삭제할 회사의 ID
     * @return Call<Void> - API 호출 결과를 담은 Call 객체
     */
    @DELETE("companies/{id}")
    fun deleteCompany(@Path("id") id: Int): Call<Void>
}
