import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.example.venturesupport.UserRole

class User(
    @SerializedName("userId") val userId: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("phone") val phone: String,
    @SerializedName("role") val role: UserRole,
    @SerializedName("password") val password: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        UserRole.valueOf(parcel.readString() ?: UserRole.COMPANY.name),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
        parcel.writeString(phone)
        parcel.writeString(role.name) // Enum의 이름을 문자열로 저장
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
