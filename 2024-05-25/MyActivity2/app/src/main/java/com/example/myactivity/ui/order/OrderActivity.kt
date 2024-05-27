package com.example.myactivity.ui.order

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.data.model.Order
import com.example.myactivity.data.network.ApiService
import com.example.myactivity.databinding.ActivityOrderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private lateinit var orderApiService: ApiService

    override suspend fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrofit2 설정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://your-api-base-url.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // OrderApiService 인스턴스 생성
        orderApiService = retrofit.create(ApiService::class.java)

        // 주문 생성 예시
        val order = Order()

        // 서버로 주문 생성 요청
        orderApiService.createOrder(order).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    // 주문 생성 성공
                    Toast.makeText(this@OrderActivity, "주문이 생성되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    // 주문 생성 실패
                    Toast.makeText(this@OrderActivity, "주문 생성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                // 통신 실패
                Toast.makeText(this@OrderActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })

        // 서버로부터 모든 주문 조회 예시
        orderApiService.getAllOrders().enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    // orders를 화면에 표시하는 작업 수행
                } else {
                    // 주문 조회 실패
                    Toast.makeText(this@OrderActivity, "주문 조회에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                // 통신 실패
                Toast.makeText(this@OrderActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

private fun <T> Response<T>.enqueue(callback: Callback<T>) {

}
