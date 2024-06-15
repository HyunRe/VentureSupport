package com.example.test2

import OrderApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit을 사용하여 서버와 통신하기 위한 객체입니다.
 */
object RetrofitService {
    private const val BASE_URL = "http://192.168.55.168:8080/api/" // 서버의 기본 URL

    /**
     * Retrofit 인스턴스를 초기화합니다.
     */
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * User 관련 API 서비스를 제공합니다.
     */
    val userService: UserApi by lazy {
        instance.create(UserApi::class.java)
    }

    /**
     * Auth 관련 API 서비스를 제공합니다.
     */
    val authService: AuthApi by lazy {
        instance.create(AuthApi::class.java)
    }

    /**
     * Order 관련 API 서비스를 제공합니다.
     */
    val orderService: OrderApi by lazy {
        instance.create(OrderApi::class.java)
    }

    /**
     * Product 관련 API 서비스를 제공합니다.
     */
    val productService: ProductApi by lazy {
        instance.create(ProductApi::class.java)
    }

    /**
     * Supplier 관련 API 서비스를 제공합니다.
     */
    val supplierService: SupplierApi by lazy {
        instance.create(SupplierApi::class.java)
    }

    /**
     * Company 관련 API 서비스를 제공합니다.
     */
    val companyService: CompanyApi by lazy {
        instance.create(CompanyApi::class.java)
    }
}
