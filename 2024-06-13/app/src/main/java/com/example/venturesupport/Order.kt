package com.example.venturesupport
import User
import android.os.Parcel
import android.os.Parcelable
import com.example.venturesupport.Company
import com.example.venturesupport.Product
import com.example.venturesupport.Supplier
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Order(
    @SerializedName("orderId") val orderId: Int?,
    @SerializedName("product") val product: Product,
    @SerializedName("supplier") val supplier: Supplier,
    @SerializedName("company") val company: Company,
    @SerializedName("user") var user: User?,
    @SerializedName("date") val date: Date,
    @SerializedName("salary") val salary: Double,
    @SerializedName("totalAmount") val totalAmount: Int
) : Parcelable {
    @Suppress("DEPRECATION")
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Product::class.java.classLoader)!!,
        parcel.readParcelable(Supplier::class.java.classLoader)!!,
        parcel.readParcelable(Company::class.java.classLoader)!!,
        parcel.readParcelable(User::class.java.classLoader)!!,
        parcel.readSerializable() as Date,
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(orderId)
        parcel.writeParcelable(product, flags)
        parcel.writeParcelable(supplier, flags)
        parcel.writeParcelable(company, flags)
        parcel.writeParcelable(user, flags)
        parcel.writeSerializable(date)
        parcel.writeDouble(salary)
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

