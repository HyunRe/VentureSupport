import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Warehouse(
    @SerializedName("warehouseId") val warehouseId: Int?,
    @SerializedName("user") val user: User,
    @SerializedName("warehouseName") val warehouseName: String,
    @SerializedName("warehouseLocation") val warehouseLocation: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(User::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(warehouseId)
        parcel.writeParcelable(user, flags)
        parcel.writeString(warehouseName)
        parcel.writeString(warehouseLocation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Warehouse> {
        override fun createFromParcel(parcel: Parcel): Warehouse {
            return Warehouse(parcel)
        }

        override fun newArray(size: Int): Array<Warehouse?> {
            return arrayOfNulls(size)
        }
    }
}
