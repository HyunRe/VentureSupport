package com.example.test2

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class VehicleInventory(
    @SerializedName("vehicleInventoryId") val vehicleInventoryId: Int?,
    @SerializedName("user") val user: User,
    @SerializedName("productName") val productName: String,
    @SerializedName("vehicleInventoryQuantity") val vehicleInventoryQuantity: String
) : Parcelable {
    @Suppress("DEPRECATION")
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(User::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(vehicleInventoryId)
        parcel.writeParcelable(user, flags)
        parcel.writeString(productName)
        parcel.writeString(vehicleInventoryQuantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VehicleInventory> {
        override fun createFromParcel(parcel: Parcel): VehicleInventory {
            return VehicleInventory(parcel)
        }

        override fun newArray(size: Int): Array<VehicleInventory?> {
            return arrayOfNulls(size)
        }
    }
}
