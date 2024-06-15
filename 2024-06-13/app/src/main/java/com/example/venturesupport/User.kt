
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * 사용자를 나타내는 데이터 클래스입니다.
 * @property userId 사용자 ID입니다.
 * @property username 사용자 이름입니다.
 * @property email 사용자 이메일 주소입니다.
 * @property lat 사용자의 위도 좌표입니다.
 * @property lng 사용자의 경도 좌표입니다.
 * @property phone 사용자 전화번호입니다.
 * @property role 사용자의 역할(UserRole 열거형)입니다.
 * @property password 사용자 비밀번호입니다.
 */
class User(
    @SerializedName("userId") val userId: Int?,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("phone") val phone: String,

    @SerializedName("password") val password: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString() ?: "",

        parcel.readString() ?: ""
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(userId)
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
        parcel.writeString(phone)

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
