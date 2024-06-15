package com.example.test2

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * 회사 정보를 표현하는 데이터 클래스입니다.
 * @property companyId Int? - 회사의 고유 식별자
 * @property companyName String - 회사 이름
 * @property companyEmail String? - 회사 이메일
 * @property companyPhoneNumber String - 회사 전화번호
 * @property companyPassword String? - 회사 비밀번호
 */
data class Company(
    @SerializedName("companyId") val companyId: Int?,
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyEmail") val companyEmail: String?,
    @SerializedName("companyPhoneNumber") val companyPhoneNumber: String,
    @SerializedName("companyPassword") val companyPassword: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(companyId)
        parcel.writeString(companyName)
        parcel.writeString(companyEmail)
        parcel.writeString(companyPhoneNumber)
        parcel.writeString(companyPassword)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Company> {
        override fun createFromParcel(parcel: Parcel): Company {
            return Company(parcel)
        }

        override fun newArray(size: Int): Array<Company?> {
            return arrayOfNulls(size)
        }
    }
}
