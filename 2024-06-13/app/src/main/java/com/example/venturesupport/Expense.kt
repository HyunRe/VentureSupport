package com.example.venturesupport

import User
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.Date

// Expense 데이터 클래스: 지출 정보를 나타내는 클래스, Parcelable을 구현하여 인텐트 간 데이터 전달 가능
data class Expense(
    @SerializedName("expense_id") val expenseId: Int?, // 지출 ID
    @SerializedName("payment") val payment: Payment, // 결제 정보
    @SerializedName("user") val user: User, // 사용자 정보
    @SerializedName("expense_details") val expenseDetails: String, // 지출 세부 사항
    @SerializedName("expense_amount") val expenseAmount: Double, // 지출 금액
    @SerializedName("expense_date") val expenseDate: Date // 지출 날짜
) : Parcelable {

    // Parcelable 인터페이스 구현: Parcel에서 객체를 생성하는 생성자
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int, // 지출 ID 읽기
        parcel.readParcelable(Payment::class.java.classLoader)!!, // 결제 정보 읽기
        parcel.readParcelable(User::class.java.classLoader)!!, // 사용자 정보 읽기
        parcel.readString()!!, // 지출 세부 사항 읽기
        parcel.readDouble(), // 지출 금액 읽기
        Date(parcel.readLong()) // 지출 날짜 읽기
    )

    // Parcel에 객체를 쓰는 메서드
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(expenseId) // 지출 ID 쓰기
        parcel.writeParcelable(payment, flags) // 결제 정보 쓰기
        parcel.writeParcelable(user, flags) // 사용자 정보 쓰기
        parcel.writeString(expenseDetails) // 지출 세부 사항 쓰기
        parcel.writeDouble(expenseAmount) // 지출 금액 쓰기
        parcel.writeLong(expenseDate.time) // 지출 날짜 쓰기
    }

    // Parcelable 인터페이스 구현: 내용 설명 메서드 (기본 값 0 반환)
    override fun describeContents() = 0

    // CREATOR 객체: Parcelable 인터페이스 구현을 위한 객체 생성 메서드 및 배열 생성 메서드 정의
    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel) = Expense(parcel) // Parcel에서 객체 생성
        override fun newArray(size: Int) = arrayOfNulls<Expense?>(size) // 객체 배열 생성
    }
}
