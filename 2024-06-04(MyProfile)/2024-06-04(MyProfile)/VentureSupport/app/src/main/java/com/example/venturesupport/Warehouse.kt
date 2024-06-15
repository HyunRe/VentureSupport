import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * 창고 정보를 표현하는 데이터 클래스입니다.
 * @property warehouseId Int? - 창고의 고유 식별자
 * @property user User - 창고를 소유한 사용자 정보
 * @property warehouseName String - 창고의 이름
 * @property warehouseLocation String - 창고의 위치
 */
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

    /**
     * 객체를 Parcel로 변환하는 메서드입니다.
     * @param parcel Parcel - 변환될 Parcel 객체
     * @param flags Int - 플래그
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(warehouseId)
        parcel.writeParcelable(user, flags)
        parcel.writeString(warehouseName)
        parcel.writeString(warehouseLocation)
    }

    /**
     * 객체의 설명을 반환합니다.
     * @return Int - 객체의 설명
     */
    override fun describeContents(): Int {
        return 0
    }

    /**
     * CREATOR 객체를 생성하는 동반 객체입니다.
     */
    companion object CREATOR : Parcelable.Creator<Warehouse> {
        /**
         * Parcel에서 객체를 생성합니다.
         * @param parcel Parcel - 생성될 Parcel 객체
         * @return Warehouse - 생성된 창고 객체
         */
        override fun createFromParcel(parcel: Parcel): Warehouse {
            return Warehouse(parcel)
        }

        /**
         * 배열을 생성합니다.
         * @param size Int - 배열의 크기
         * @return Array<Warehouse?> - 생성된 배열
         */
        override fun newArray(size: Int): Array<Warehouse?> {
            return arrayOfNulls(size)
        }
    }
}
