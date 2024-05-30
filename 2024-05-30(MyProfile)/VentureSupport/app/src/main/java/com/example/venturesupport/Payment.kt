package com.example.venturesupport

import User
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("paymentId") val paymentId: Int?,
    @SerializedName("user") val user: User,
    @SerializedName("paymentName")val paymentName: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(User::class.java.classLoader)!!,
        parcel.readString()!!,
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
