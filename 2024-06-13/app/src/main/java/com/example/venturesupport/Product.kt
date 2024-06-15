package com.example.venturesupport

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId") val productId: Int?, //상품 일련번호
    @SerializedName("productName") val productName: String?, //상품명
    @SerializedName("productPrice") val productPrice: Double?, //가격
    @SerializedName("productQuantity") val productQuantity: String? //개수:  String 타입으로 변경
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString() // String 타입으로 변경
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(productId)
        parcel.writeString(productName)
        parcel.writeValue(productPrice)
        parcel.writeString(productQuantity) // String 타입으로 변경
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
