package com.example.test2

import Product
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ProductInformation(
    @SerializedName("productInformationId") val productInformationId: Int?,
    @SerializedName("order") var order: Order?,
    @SerializedName("product") var product: Product?
) : Parcelable {
    @Suppress("DEPRECATION")
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Order::class.java.classLoader),
        parcel.readParcelable(Product::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(productInformationId)
        parcel.writeParcelable(order, flags)
        parcel.writeParcelable(product, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductInformation> {
        override fun createFromParcel(parcel: Parcel): ProductInformation {
            return ProductInformation(parcel)
        }

        override fun newArray(size: Int): Array<ProductInformation?> {
            return arrayOfNulls(size)
        }
    }
}
