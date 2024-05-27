//단일 Retrofit 인스턴스를 사용하여 모든 API 호출을 처리

package com.example.myactivity.data.network

import com.example.myactivity.data.model.ApiResponse
import com.example.myactivity.data.model.Order
import com.example.myactivity.data.model.Product
import com.example.myactivity.data.model.Shipper
import com.example.myactivity.data.model.User
import retrofit2.Response



class ApiRepository {

    suspend fun getProducts(): Response<List<Product>> {
        return RetrofitClient.apiService.getProducts()
    }

    suspend fun getProductById(productId: Long): Response<Product> {
        return RetrofitClient.apiService.getProductById(productId)
    }

    suspend fun getShippers(): Response<List<Shipper>> {
        return RetrofitClient.apiService.getShippers()
    }

    suspend fun getShipperById(shipperId: Long): Response<Shipper> {
        return RetrofitClient.apiService.getShipperById(shipperId)
    }

    suspend fun deleteShipperById(shipperId: Long): Response<Void> {
        return RetrofitClient.apiService.deleteShipperById(shipperId)
    }

    suspend fun getOrders(): Response<List<Order>> {
        return RetrofitClient.apiService.getOrders()
    }

    suspend fun getOrderById(orderId: Long): Response<Order> {
        return RetrofitClient.apiService.getOrderById(orderId)
    }

    suspend fun createOrder(order: Order): Response<Order> {
        return RetrofitClient.apiService.createOrder(order)
    }

    suspend fun addProductToOrder(orderId: Long, product: Product): Response<Order> {
        return RetrofitClient.apiService.addProductToOrder(orderId, product)
    }

    suspend fun login(loginRequest: LoginRequest): Response<Boolean> {
        return RetrofitClient.apiService.login(loginRequest)
    }

    suspend fun getUserById(userId: Long): Response<User> {
        return RetrofitClient.apiService.getUserById(userId)
    }

    suspend fun registerUser(user: User): Response<ApiResponse> {
        return RetrofitClient.apiService.registerUser(user)
    }

    suspend fun naverLogin(accessToken: String): Response<Boolean> {
        return RetrofitClient.apiService.naverLogin(accessToken)
    }
}