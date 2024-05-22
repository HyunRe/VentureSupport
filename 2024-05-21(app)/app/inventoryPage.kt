import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Product(
    val productId: Int,
    val name: String,
    val price: Double,
    val quantity: Int
)

interface ProductService {
    @GET("/products")
    suspend fun getProducts(): List<Product>
}

object RetrofitInstance {
    private const val BASE_URL = "http://223.194.157.56:8080/product"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

class InventoryPage : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_page)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter()
        recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val products = getProducts()
                adapter.submitList(products)
            } catch (e: Exception) {
                Toast.makeText(this@InventoryPage, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun getProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            val productService = RetrofitInstance.retrofit.create(ProductService::class.java)
            productService.getProducts()
        }
    }
}
