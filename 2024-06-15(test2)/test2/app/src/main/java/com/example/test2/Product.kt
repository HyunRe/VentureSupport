import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId") val productId: Int?,
    @SerializedName("productName") val productName: String?,
    @SerializedName("productPrice") val productPrice: Int?, // Int로 변경
    @SerializedName("productQuantity") val productQuantity: String? // String 타입으로 변경
) : Parcelable {
    @Suppress("DEPRECATION")
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int, // Int로 변경
        parcel.readString() // String 타입으로 변경
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(productId)
        parcel.writeString(productName)
        parcel.writeValue(productPrice) // Int로 변경
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
