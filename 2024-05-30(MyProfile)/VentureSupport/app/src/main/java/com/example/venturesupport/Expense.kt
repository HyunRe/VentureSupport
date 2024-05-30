package com.example.venturesupport

import User
import java.util.Date

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Expense(
    @SerializedName("expense_id") val expenseId: Int?,
    @SerializedName("payment") val payment: Payment,
    @SerializedName("user") val user: User,
    @SerializedName("expense_details") val expenseDetails: String,
    @SerializedName("expense_amount") val expenseAmount: Double,
    @SerializedName("expense_date") val expenseDate: Date
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Payment::class.java.classLoader)!!,
        parcel.readParcelable(User::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readDouble(),
        Date(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(expenseId)
        parcel.writeParcelable(payment, flags)
        parcel.writeParcelable(user, flags)
        parcel.writeString(expenseDetails)
        parcel.writeDouble(expenseAmount)
        parcel.writeLong(expenseDate.time)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel) = Expense(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Expense?>(size)
    }
}