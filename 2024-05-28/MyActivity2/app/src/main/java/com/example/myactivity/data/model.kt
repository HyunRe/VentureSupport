package com.example.myactivity.data.model
//주소 클래스

import java.sql.Date
import java.time.LocalDate

//로그인 유저 구분 클래스
/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
Address(
Income(
IncomeId(
ApiResponse(
Order(
Payment(
Product(
Shipper(
User(
VehicleInventory (
Warehouses(

 */
class LoggedInUser(val id: String, @JvmField val displayName: String)


data class Address(
    val id: Long,
    val street: String?,
    val city: String?,
    val state: String?,
    val zipcode: String?,
    val companyUserId: Long?,
    val order: Order?
)


data class Income(
    val user: List<User>, //사용
    val shipmentId: Long, //사용
    val amount: Double, //confirm, 즉 접수된 원장에 대해 계산 필요
    val date: LocalDate?
)

//총수입
data class IncomeId(
    val user: List<User>,
    val shipmentId: Long
)


data class ApiResponse(
    val success: Boolean,
    val roll: String?,
    val message: String?
)

//주문 정보

data class Order(
    val orderId: Int,
    val userId: String,
    val date: Date,
    val supplierName: String,
    val supplierPhoneNumber: String,
    val supplierLocation: String,
    val salary: Double,

    //val address: List<Address>,
    //val products: List<Product>,// Assuming Product is another data class
    //val shipper: String? = null,

    )


data class Payment(
    val paymentId: Int,
    val userId: User,
    val paymentName: String,
)

//상품 정보
data class Product(
    val productid: Int,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    //val companyUserId: Long,
)

data class ProductInformationId(
    val orderId: Int,
    val productId: Int,
    val userId: Int,
)

data class ProductInformation(
    val id: ProductInformationId,
    val orderId: Order,
    val productId: Product,
    val userId: User,
)


//운송자 정보
data class Shipper(
    val productId: Long,
    val name: String?,
    val phone: String?,
    val available: Boolean
)

enum class UserRole (
    SUPPLIER: String, DRIVER: String
)

//유저 정보
// User.kt
data class User(
    val userId: Int,
    val username: String?, //이용자 이름
    val password: String,
    val email: String?,
    val phone: String,
    val lat: Double?,//위도
    val lng: Double?, //경도
    //val navId: String?,
    val role: UserRole
)


data class VehicleInventory(
    val inventoryId: Int,
    val productId: Product,
    val userId: User,
    val quantity: String
)


data class Warehouse (
    val warehouseId: Int,
    val userId: User,
    val warehouseName: String,
    val warehouseLocation: String,
)

