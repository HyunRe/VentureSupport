import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.app.model.Address
import com.example.app.model.Order
import com.example.app.model.Product
import com.example.app.network.RetrofitClient
import com.example.app.databinding.ActivityOrderBinding
import com.example.app.sevice.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.app.model.ApiResponse

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // '원장 접수' 버튼 클릭 리스너 설정
        binding.buttonSubmitLedger.setOnClickListener {
            attemptSubmitLedger()
        }
    }

    // '원장 접수' 버튼 클릭 시 호출되는 함수
    private fun attemptSubmitLedger() {
        // 입력된 값들 가져오기
        val orderId = binding.TextOrderId.text.toString().toIntOrNull() ?: 0
        val userId = binding.TextUserId.text.toString()
        val date = binding.editTextOrderDate.text.toString()
        val customerName = binding.editTextCustomerName.text.toString()
        // 배송지 정보 가져오기
        val street = binding.editTextStreet.text.toString()
        val city = binding.editTextCity.text.toString()
        val state = binding.editTextState.text.toString()
        val zipcode = binding.editTextZipcode.text.toString()
        val addressList = mutableListOf<Address>()
        if (street.isNotEmpty() || city.isNotEmpty() || state.isNotEmpty() || zipcode.isNotEmpty()) {
            val address = Address(0, street, city, state, zipcode, null, null)
            addressList.add(address)
        }
        // 상품 정보 가져오기
        val productId = binding.editTextProductId.text.toString().toLongOrNull() ?: 0
        val productName = binding.editTextProductName.text.toString()
        val productPrice = binding.editTextProductPrice.text.toString().toDoubleOrNull() ?: 0.0
        val productQuantity = binding.editTextProductQuantity.text.toString().toIntOrNull() ?: 0
        val companyUserId = 0L // Change this value according to your requirement
        val productList = mutableListOf<Product>()
        if (productId.toLong() != 0L.toLong() && productName.isNotEmpty() && productPrice != 0.0 && productQuantity != 0) {
            val product =
                Product(productId, productName, productPrice, productQuantity, companyUserId)
            productList.add(product)
        }
        // 배송업체 정보 가져오기
        val shipper = binding.editTextShipper.text.toString()

        // Order 객체 생성
        val order = Order(orderId, userId, date, customerName, addressList, productList, shipper)

        // 주문 요청을 서버에 전송하고 응답을 처리하는 함수
        suspend fun submitOrder(order: Order) {
            RetrofitClient.apiService.createOrder(order).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        // 주문 성공
                        val apiResponse = response.body()
                        if (apiResponse != null) {
                            Log.d("OrderActivity", "Order submitted successfully")

                        } else {
                            // 서버 응답이 null인 경우
                            Log.e("OrderActivity", "Failed to submit order: response body is null")
                        }
                    } else {
                        // 서버 응답이 실패인 경우
                        Log.e("OrderActivity", "Failed to submit order: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    // 네트워크 요청 실패
                    Log.e("OrderActivity", "Error submitting order: ${t.message}")
                }

            })
        }
    }
}


