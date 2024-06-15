package com.example.test2

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * 공급자 정보를 나타내는 데이터 클래스입니다.
 */
data class Supplier(
    @SerializedName("supplierId") val supplierId: Int?, // 공급자 ID
    @SerializedName("supplierName") val supplierName: String?, // 공급자 이름
    @SerializedName("supplierPhoneNumber") val supplierPhoneNumber: String?, // 공급자 전화번호
    @SerializedName("supplierLocation") val supplierLocation: String? // 공급자 위치
) : Parcelable {
    // 파서로부터 객체를 생성하기 위한 생성자
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int, // 공급자 ID
        parcel.readString() ?: "", // 공급자 이름
        parcel.readString() ?: "", // 공급자 전화번호
        parcel.readString() ?: ""  // 공급자 위치
    )

    /**
     * 객체를 Parcel에 쓰기 위한 메서드입니다.
     * @param parcel Parcel - 쓰기 대상 Parcel
     * @param flags Int - 플래그
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(supplierId)
        parcel.writeString(supplierName)
        parcel.writeString(supplierPhoneNumber)
        parcel.writeString(supplierLocation)
    }

    /**
     * Parcelable 인터페이스의 구현 메서드로, 객체의 콘텐츠 설명을 반환합니다.
     */
    override fun describeContents(): Int {
        return 0
    }

    // Parcelable.Creator 인터페이스를 구현하여 객체 생성을 지원합니다.
    companion object CREATOR : Parcelable.Creator<Supplier> {
        override fun createFromParcel(parcel: Parcel): Supplier {
            return Supplier(parcel)
        }

        override fun newArray(size: Int): Array<Supplier?> {
            return arrayOfNulls(size)
        }
    }
}
