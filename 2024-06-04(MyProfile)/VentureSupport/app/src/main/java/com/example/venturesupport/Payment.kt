package com.example.venturesupport

import User
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

// 결제 데이터 모델 클래스
data class Payment(
    @SerializedName("paymentId") val paymentId: Int?, // 결제 ID
    @SerializedName("user") val user: User, // 결제를 한 사용자 정보
    @SerializedName("paymentName") val paymentName: String // 결제 방법 이름
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(User::class.java.classLoader)!!,
        parcel.readString()!!
    )

    // writeToParcel 함수: 객체를 Parcel 객체로 변환
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(paymentId)
        parcel.writeParcelable(user, flags)
        parcel.writeString(paymentName)
    }

    // describeContents 함수: 반환하는 값의 플래그
    override fun describeContents(): Int {
        return 0
    }

    // CREATOR 객체: Parcelable.Creator 인터페이스를 구현하여 Parcelable 객체를 만들기 위한 역할
    companion object CREATOR : Parcelable.Creator<Payment> {
        // createFromParcel 함수: Parcel 객체에서 Parcelable 객체를 만드는 역할
        override fun createFromParcel(parcel: Parcel): Payment {
            return Payment(parcel)
        }

        // newArray 함수: 배열의 크기를 지정하여 새로운 배열을 만드는 역할
        override fun newArray(size: Int): Array<Payment?> {
            return arrayOfNulls(size)
        }
    }
}
