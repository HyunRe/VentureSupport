package com.example.venturesupport

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "http://100.26.31.51:8080/api/" // http://223.194.157.56:8080/api/

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userService: UserApi by lazy {
        instance.create(UserApi::class.java)
    }

    val warehouseService: WarehouseApi by lazy {
        instance.create(WarehouseApi::class.java)
    }

    val paymentService: PaymentApi by lazy {
        instance.create(PaymentApi::class.java)
    }

    val expenseService: ExpenseApi by lazy {
        instance.create(ExpenseApi::class.java)
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

    val supplierSevice: SupplierApi by lazy {
        instance.create(SupplierApi::class.java)
    }

    val companyService: CompanyApi by lazy {
        instance.create(CompanyApi::class.java)
    }

    val vehicleInventoryService: VehicleInventoryApi by lazy {
        instance.create(VehicleInventoryApi::class.java)
    }

}