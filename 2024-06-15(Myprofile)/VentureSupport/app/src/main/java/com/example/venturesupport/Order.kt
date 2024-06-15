package com.example.venturesupport

import com.google.gson.annotations.SerializedName
import android.os.Parcel
import android.os.Parcelable

data class Order(
    @SerializedName("orderId") val orderId: Int?,
    @SerializedName("supplier") var supplier: Supplier,
    @SerializedName("company") val company: Company,
    @SerializedName("user") var user: User?,
    @SerializedName("date") val date: String,
    @SerializedName("salary") val salary: Int,
    @SerializedName("totalAmount") val totalAmount: Int
) : Parcelable {
    @Suppress("DEPRECATION")
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Supplier::class.java.classLoader)!!,
        parcel.readParcelable(Company::class.java.classLoader)!!,
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(orderId)
        parcel.writeParcelable(supplier, flags)
        parcel.writeParcelable(company, flags)
        parcel.writeParcelable(user, flags)
        parcel.writeString(date)
        parcel.writeInt(salary)
        parcel.writeInt(totalAmount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}
