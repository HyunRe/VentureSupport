package com.example.test2

import OrderApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = " http://192.168.55.168:8080/api/" // http://223.194.157.56:8080/login

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userService: UserApi by lazy {
        instance.create(UserApi::class.java)
    }

    val authService: AuthApi by lazy {
        instance.create(AuthApi::class.java)
    }

    val orderService: OrderApi by lazy {
        instance.create(OrderApi::class.java)
    }

    val productService: ProductApi by lazy {
        instance.create(ProductApi::class.java)
    }

    val SupplierSevice: SupplierApi by lazy {
        instance.create(SupplierApi::class.java)
    }

    val CompanyService: CompanyApi by lazy {
        instance.create(CompanyApi::class.java)
    }
}