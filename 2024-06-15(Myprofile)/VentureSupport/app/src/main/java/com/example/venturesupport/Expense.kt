package com.example.venturesupport

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Expense(
    @SerializedName("expense_id") val expenseId: Int?,
    @SerializedName("payment") val payment: Payment,
    @SerializedName("user") val user: User,
    @SerializedName("expense_details") val expenseDetails: String,
    @SerializedName("expense_amount") val expenseAmount: Int,
    @SerializedName("expense_date") val expenseDate: String
) : Parcelable {
    @Suppress("DEPRECATION")
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Payment::class.java.classLoader)!!,
        parcel.readParcelable(User::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(expenseId)
        parcel.writeParcelable(payment, flags)
        parcel.writeParcelable(user, flags)
        parcel.writeString(expenseDetails)
        parcel.writeInt(expenseAmount)
        parcel.writeString(expenseDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel): Expense {
            return Expense(parcel)
        }

        override fun newArray(size: Int): Array<Expense?> {
            return arrayOfNulls(size)
        }
    }
}
