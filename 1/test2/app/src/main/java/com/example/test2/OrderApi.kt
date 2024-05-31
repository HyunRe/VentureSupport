
import com.example.test2.Order
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderApi {

    @GET("orders")
    fun getAllOrders(): Call<List<Order>>

    @GET("orders/{id}")
    fun getOrderById(@Path("id") id: Int): Call<Order>

    @GET("orders/users/{id}")
    fun getOrdersByUserId(@Path("id") id: Int): Call<List<Order>>

    @GET("orders/suppliers/{id}")
    fun getOrdersByCompanyId(@Path("id") id: Int): Call<List<Order>>

    @POST("orders")
    fun createOrder(@Body order: Order): Call<Order>

    @PUT("orders/{id}")
    fun updateOrder(@Path("id") id: Int, @Body order: Order): Call<Order>

    @DELETE("orders/{id}")
    fun deleteOrder(@Path("id") id: Int): Call<Void>
}
