import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WarehouseApi {

    @POST("warehouses")
    fun createWarehouse(@Body warehouse: Warehouse): Call<Warehouse>

    @GET("warehouses/users/{id}")
    fun getWarehousesByUserId(@Path("id") id: Int): Call<List<Warehouse>>

    @DELETE("warehouses/{id}")
    fun deleteWarehouse(@Path("id") id: Int): Call<Void>
}
