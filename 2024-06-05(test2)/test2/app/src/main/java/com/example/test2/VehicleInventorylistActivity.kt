package com.example.test2

import Product
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.VehicleinventorylistBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 차량 재고 목록 화면을 관리하는 Activity입니다.
 */
class VehicleInventorylistActivity: AppCompatActivity() {
    // 바인딩 변수: 레이아웃과 연결하여 UI 요소에 접근할 수 있도록 합니다.
    private val binding: VehicleinventorylistBinding by lazy {
        VehicleinventorylistBinding.inflate(layoutInflater)
    }

    // 차량 재고 리스트를 저장할 변수
    private lateinit var vehicleInventorylistAdapter: VehicleInventorylistAdapter
    private var productLists = ArrayList<Product>()
    private var intentOrder: Order? = null
    private var intentUser: User? = null

    /**
     * 액티비티가 생성될 때 호출됩니다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 인텐트를 통해 전달된 사용자와 주문 정보를 가져옵니다.
        @Suppress("DEPRECATION")
        intentUser = intent.getParcelableExtra("intentUser")
        @Suppress("DEPRECATION")
        intentOrder = intent.getParcelableExtra("intentOrder")

        // 사용자 ID로 주문 정보를 가져옵니다.
        getOrdersByUserId(intentUser?.userId!!)

        // 차량 재고 목록 RecyclerView 설정
        vehicleInventorylistAdapter = VehicleInventorylistAdapter(productLists)
        binding.inventoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.inventoryRecyclerView.adapter = vehicleInventorylistAdapter
        vehicleInventorylistAdapter.notifyDataSetChanged()

        // 플로팅 액션 버튼 클릭 리스너 설정: 차량 재고 추가 화면으로 이동
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddVehicleInventoryActivity::class.java)
            intent.putExtra("intentUser", intentUser)
            intent.putExtra("intentOrder", intentOrder)
            startActivity(intent)
        }
    }

    /**
     * 사용자 ID로 주문 정보를 가져오는 함수입니다.
     * @param userId Int - 사용자 ID
     */
    private fun getOrdersByUserId(userId: Int) {
        val call = RetrofitService.orderService.getOrdersByUserId(userId)
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    if (orders != null) {
                        // 각 주문에서 제품을 추출하여 리스트에 추가합니다.
                        for (order in orders) {
                            val product = order.product
                            if (product != null) {
                                productLists.addAll(listOf(product))
                                Log.d("IncomeChartActivity", "Products added to list: $product")
                            } else {
                                Log.e("IncomeChartActivity", "No products found in order: $order")
                            }
                        }
                    } else {
                        Log.e("IncomeChartActivity", "No order data found in response")
                    }
                } else {
                    Log.e("IncomeChartActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("IncomeChartActivity", "Network request failed", t)
            }
        })
    }
}
