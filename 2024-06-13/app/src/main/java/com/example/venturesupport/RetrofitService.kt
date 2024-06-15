package com.example.venturesupport

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "http://100.26.31.51:8080/api/" // http://223.194.157.56:8080/api/

    //리트로핏 사용 함수
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    //이용자 데이터베이스 사용 관련 api
    val userService: UserApi by lazy {
        instance.create(UserApi::class.java)
    }

    //창고 데이터베이스 사용 관련 api
    val warehouseService: WarehouseApi by lazy {
        instance.create(WarehouseApi::class.java)
    }

    //결제수단 데이터베이스 사용 관련 api
    val paymentService: PaymentApi by lazy {
        instance.create(PaymentApi::class.java)
    }
    //지출내역 데이터베이스 사용 관련 api
    val expenseService: ExpenseApi by lazy {
        instance.create(ExpenseApi::class.java)
    }

    //회원관리 데이터베이스 사용 관련 api
    val authService: AuthApi by lazy {
        instance.create(AuthApi::class.java)
    }

    //주문정보 데이터베이스 사용 관련 api
    val orderService: OrderApi by lazy {
        instance.create(OrderApi::class.java)
    }

    //상품정보 데이터베이스 사용 관련 api
    val productService: ProductApi by lazy {
        instance.create(ProductApi::class.java)
    }

    //공급자(운송기사) 데이터베이스 사용 관련 api
    val supplierSevice: SupplierApi by lazy {
        instance.create(SupplierApi::class.java)
    }

    //구매점주 데이터베이스 사용 관련 api
    val companyService: CompanyApi by lazy {
        instance.create(CompanyApi::class.java)
    }

    //차량내 재고 데이터베이스 사용 관련 api
    val vehicleInventoryService: VehicleInventoryApi by lazy {
        instance.create(VehicleInventoryApi::class.java)
    }

}