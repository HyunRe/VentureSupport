package com.example.venturesupport

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("userId") val userId: Int?,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String,
    @SerializedName("userPhoneNumber") val userPhoneNumber: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lng") val lng: Double?,
    @SerializedName("userPassword") val userPassword: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString()!!
    )

    // toString 메서드 오버라이드
    override fun toString(): String {
        return "User(userId=$userId, username=$username, email=$email, userPhoneNumber=$userPhoneNumber, lat=$lat, lng=$lng, userPassword=$userPassword)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(userId)
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeString(userPhoneNumber)
        parcel.writeValue(lat)
        parcel.writeValue(lng)
        parcel.writeString(userPassword)
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
