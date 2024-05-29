package com.example.myactivity.data

import com.example.myactivity.data.service.AuthService
import com.example.myactivity.data.service.InfoService
import com.example.myactivity.data.service.OrderService
import com.example.myactivity.data.service.PaymentService
import com.example.myactivity.data.service.ProductService
import com.example.myactivity.data.service.UserService
import com.example.myactivity.data.service.VehicleService
import com.example.myactivity.data.service.WarehouseService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// RetrofitClient.kt
// 클라이언트.
object RetrofitClient {
    private const val BASE_URL = "http://localhost:8080"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val authService: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    val orderService: OrderService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OrderService::class.java)
    }

    val paymentService: PaymentService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PaymentService::class.java)
    }

    val infoService: InfoService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InfoService::class.java)
    }
    val productService: ProductService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)
    }
    val userService: UserService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }
    val vehicleService: VehicleService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VehicleService::class.java)
    }
    val warehouseService: WarehouseService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WarehouseService::class.java)
    }
}