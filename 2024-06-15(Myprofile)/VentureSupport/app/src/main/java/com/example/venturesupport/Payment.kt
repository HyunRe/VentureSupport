package com.example.venturesupport

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(paymentId)
        parcel.writeParcelable(user, flags)
        parcel.writeString(paymentName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Payment> {
        override fun createFromParcel(parcel: Parcel): Payment {
            return Payment(parcel)
        }

        override fun newArray(size: Int): Array<Payment?> {
            return arrayOfNulls(size)
        }
    }
}
