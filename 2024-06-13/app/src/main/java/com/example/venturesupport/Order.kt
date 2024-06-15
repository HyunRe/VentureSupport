package com.example.venturesupport
import User
import android.os.Parcel
import android.os.Parcelable
import com.example.venturesupport.Company
import com.example.venturesupport.Product
import com.example.venturesupport.Supplier
import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * 주문 정보를 담는 데이터 클래스입니다.
 */
data class Order(
    @SerializedName("orderId") val orderId: Int?, // 주문 ID
    @SerializedName("product") val product: Product, // 제품 정보
    @SerializedName("supplier") val supplier: Supplier, // 공급자 정보
    @SerializedName("company") val company: Company, // 회사 정보
    @SerializedName("user") var user: User?, // 사용자 정보, 선택적
    @SerializedName("date") val date: Date, // 주문 날짜
    @SerializedName("salary") val salary: Double, // 급여
    @SerializedName("totalAmount") val totalAmount: Int // 총 금액
) : Parcelable {

    // Parcelable 인터페이스 구현을 위한 생성자
    @Suppress("DEPRECATION")
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int, // 주문 ID
        parcel.readParcelable(Product::class.java.classLoader)!!, // 제품 정보
        parcel.readParcelable(Supplier::class.java.classLoader)!!, // 공급자 정보
        parcel.readParcelable(Company::class.java.classLoader)!!, // 회사 정보
        parcel.readParcelable(User::class.java.classLoader)!!, // 사용자 정보
        parcel.readSerializable() as Date, // 주문 날짜
        parcel.readDouble(), // 급여
        parcel.readInt() // 총 금액
    )

    // Parcelable 인터페이스 구현: 객체를 Parcel에 쓰는 메서드
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

    // Parcelable 인터페이스 구현: 설명 콘텐츠의 설명자를 반환하는 메서드
    override fun describeContents(): Int {
        return 0
    }

    // Parcelable 인터페이스 구현: Order 객체를 생성하는 객체 생성자
    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}
