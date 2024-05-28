package com.example.myactivity.data.network

import com.example.myactivity.data.model.Order
import com.example.myactivity.data.model.Payment
import com.example.myactivity.data.model.Product
import com.example.myactivity.data.model.ProductInformation
import com.example.myactivity.data.model.Shipper
import com.example.myactivity.data.model.User
import com.example.myactivity.data.model.VehicleInventory
import com.example.myactivity.data.model.Warehouse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


//등록할 api들 더 추가될 수 있음.
interface ApiService {

    // User-related endpoints
    @GET("/users/{id}")
    suspend fun getUserById(@Path("id") userId: Long): Response<User>

    // Product-related endpoints
    @GET("/api/products")
    fun getAllProducts(): Call<List<Product>>

    @GET("/api/products/{id}")
    fun getProductById(@Path("id") id: Int): Call<Product>

    @POST("/api/products")
    fun createProduct(@Body product: Product): Call<Product>

    @PUT("/api/products/{id}")
    fun updateProduct(@Path("id") id: Int, @Body product: Product): Call<Product>

    @DELETE("/api/products/{id}")
    fun deleteProduct(@Path("id") id: Int): Call<Void>

    // Shipper-related endpoints
    @GET("/shippers")
    suspend fun getShippers(): Response<List<Shipper>>

    @GET("/shippers/{id}")
    suspend fun getShipperById(@Path("id") shipperId: Long): Response<Shipper>

    @DELETE("/shippers/{id}")
    suspend fun deleteShipperById(@Path("id") shipperId: Long): Response<Void>

    // Order-related endpoints
    @GET("/api/orders")
    fun getAllOrders(): Call<List<Order>>

    @GET("/api/orders/{id}")
    fun getOrderById(@Path("id") id: Int): Call<Order>

    @POST("/api/orders")
    fun createOrder(@Body order: Order): Call<Order>

    @PUT("/api/orders/{id}")
    fun updateOrder(@Path("id") id: Int, @Body order: Order): Call<Order>

    @DELETE("/api/orders/{id}")
    fun deleteOrder(@Path("id") id: Int): Call<Void>

    @POST("/orders/{orderId}/products")
    suspend fun addProductToOrder(
        @Path("orderId") orderId: Long,
        @Body product: Product
    ): Response<Order>

    // Authentication endpoints
    @POST("/api/auth/register")
    fun registerUser(@Body user: User): Call<String>
    @POST("/api/auth/login")
    fun loginUser(@Body user: User): Call<String>

    @POST("api/navLogin")
    fun naverLogin(@Body accessToken: String): Response<Boolean>

    //차량 endpoints
    @GET("/api/vehicle-inventory")
    fun getAllVehicleInventory(): Call<List<VehicleInventory>>

    @GET("/api/vehicle-inventory/{id}")
    fun getVehicleInventoryById(@Path("id") id: Int): Call<VehicleInventory>

    @POST("/api/vehicle-inventory")
    fun createVehicleInventory(@Body vehicleInventory: VehicleInventory): Call<VehicleInventory>

    @PUT("/api/vehicle-inventory/{id}")
    fun updateVehicleInventory(@Path("id") id: Int, @Body vehicleInventory: VehicleInventory): Call<VehicleInventory>

    @DELETE("/api/vehicle-inventory/{id}")
    fun deleteVehicleInventory(@Path("id") id: Int): Call<Void>

    //디테일 물품정보

    @GET("/api/product-information")
    fun getAllProductInformation(): Call<List<ProductInformation>>

    @GET("/api/product-information/{orderId}/{productId}/{userId}")
    fun getProductInformationById(@Path("orderId") orderId: Int, @Path("productId") productId: Int, @Path("userId") userId: Int): Call<ProductInformation>

    @POST("/api/product-information")
    fun createProductInformation(@Body productInformation: ProductInformation): Call<ProductInformation>

    @PUT("/api/product-information")
    fun updateProductInformation(@Body productInformation: ProductInformation): Call<ProductInformation>

    @DELETE("/api/product-information/{orderId}/{productId}/{userId}")
    fun deleteProductInformation(@Path("orderId") orderId: Int, @Path("productId") productId: Int, @Path("userId") userId: Int): Call<Void>

    //창고
    @GET("/api/warehouses")
    fun getAllWarehouses(): Call<List<Warehouse>>

    @GET("/api/warehouses/{id}")
    fun getWarehouseById(@Path("id") id: Int): Call<Warehouse>

    @POST("/api/warehouses")
    fun createWarehouse(@Body warehouse: Warehouse): Call<Warehouse>

    @PUT("/api/warehouses/{id}")
    fun updateWarehouse(@Path("id") id: Int, @Body warehouse: Warehouse): Call<Warehouse>

    @DELETE("/api/warehouses/{id}")
    fun deleteWarehouse(@Path("id") id: Int): Call<Void>

    // Payment API Endpoints
    @GET("/api/payments")
    fun getAllPayments(): Call<List<Payment>>

    @GET("/api/payments/{id}")
    fun getPaymentById(@Path("id") id: Int): Call<Payment>

    @POST("/api/payments")
    fun createPayment(@Body payment: Payment): Call<Payment>

    @PUT("/api/payments/{id}")
    fun updatePayment(@Path("id") id: Int, @Body payment: Payment): Call<Payment>

    @DELETE("/api/payments/{id}")
    fun deletePayment(@Path("id") id: Int): Call<Void>
}