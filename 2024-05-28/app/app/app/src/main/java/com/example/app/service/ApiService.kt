package com.example.app.sevice

import com.example.myactivity.data.LoginData
import com.example.myactivity.data.SignUpData
import com.example.myactivity.data.model.ApiResponse
import com.example.myactivity.data.model.Order
import com.example.myactivity.data.model.Product
import com.example.myactivity.data.model.Shipper
import com.example.myactivity.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

//등록할 api들 더 추가될 수 있음.
interface ApiService {

    // User-related endpoints
    @GET("/users/{id}")
    suspend fun getUserById(@Path("id") userId: Long): Response<User>

    // Product-related endpoints
    @GET("/products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("/products/{productId}")
    suspend fun getProductById(@Path("productId") productId: Long): Response<Product>

    // Shipper-related endpoints
    @GET("/shippers")
    suspend fun getShippers(): Response<List<Shipper>>

    @GET("/shippers/{id}")
    suspend fun getShipperById(@Path("id") shipperId: Long): Response<Shipper>

    @DELETE("/shippers/{id}")
    suspend fun deleteShipperById(@Path("id") shipperId: Long): Response<Void>

    // Order-related endpoints
    @GET("/orders")
    suspend fun getOrders(): Response<List<Order>>

    @GET("/orders/{orderId}")
    suspend fun getOrderById(@Path("orderId") orderId: Long): Response<Order>

    @POST("/orders")
    suspend fun createOrder(@Body order: Order): Response<Order>

    @POST("/orders/{orderId}/products")
    suspend fun addProductToOrder(@Path("orderId") orderId: Long, @Body product: Product): Response<Order>

    // Authentication endpoints
    @POST("api/login")
    suspend fun loginUser(@Body loginRequest: LoginData): Response<Boolean>

    @POST("api/users/register")
    suspend fun registerUser(@Body user: SignUpData): Response<ApiResponse>

    @POST("api/navLogin")
    suspend fun naverLogin(@Body accessToken: String): Response<Boolean>
}
