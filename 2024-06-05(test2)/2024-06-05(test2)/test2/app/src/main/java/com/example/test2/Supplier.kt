package com.example.test2
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Supplier(
    @SerializedName("supplierId") val supplierId: Int?,
    @SerializedName("supplierName") val supplierName: String?,
    @SerializedName("supplierPhoneNumber") val supplierPhoneNumber: String?,
    @SerializedName("supplierLocation") val supplierLocation: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(supplierId)
        parcel.writeString(supplierName)
        parcel.writeString(supplierPhoneNumber)
        parcel.writeString(supplierLocation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Supplier> {
        override fun createFromParcel(parcel: Parcel): Supplier {
            return Supplier(parcel)
        }

        override fun newArray(size: Int): Array<Supplier?> {
            return arrayOfNulls(size)
        }
    }
}
