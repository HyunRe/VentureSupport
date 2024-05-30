import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId") val productId: Int?,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("quantity") val quantity: Int
)
