package com.example.venturesupport

import User
import com.google.gson.annotations.SerializedName
import java.util.Date

// 주문 데이터 모델 클래스
data class Order(
    @SerializedName("orderId") val orderId: Int?, // 주문 ID
    @SerializedName("date") val date: Date, // 주문 날짜
    @SerializedName("supplierName") val supplierName: String, // 공급업체 이름
    @SerializedName("supplierPhoneNumber") val supplierPhoneNumber: String, // 공급업체 전화번호
    @SerializedName("supplierLocation") val supplierLocation: String, // 공급업체 위치
    @SerializedName("salary") val salary: Double, // 급여
    @SerializedName("totalAmount") val totalAmount: Int, // 총 금액
    @SerializedName("user") val user: User // 주문을 한 사용자 정보
)
