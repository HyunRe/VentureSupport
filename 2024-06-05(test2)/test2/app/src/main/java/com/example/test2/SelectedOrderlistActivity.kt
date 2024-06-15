package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.SelectedorderlistBinding
import com.example.test2.databinding.SelectedorderlistItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 선택된 주문 목록을 보여주는 액티비티 클래스입니다.
 */
class SelectedOrderlistActivity: AppCompatActivity() {
    // 바인딩 변수: 레이아웃과 연결하여 UI 요소에 접근할 수 있도록 합니다.
    private val binding: SelectedorderlistBinding by lazy {
        SelectedorderlistBinding.inflate(layoutInflater)
    }

    // 어댑터와 데이터 리스트 초기화
    private lateinit var selectedOrderlistAdapter: SelectedOrderlistAdapter
    private var selectedOrderLists = ArrayList<Order>()
    private var intentUser: User? = null

    /**
     * 액티비티가 생성될 때 호출됩니다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 인텐트를 통해 전달된 사용자 정보를 가져옵니다.
        @Suppress("DEPRECATION")
        intentUser = intent.getParcelableExtra("intentUser")

        // 사용자 ID로 주문 정보를 가져옵니다.
        getOrdersByUserId(intentUser?.userId!!)

        // 주문 리스트의 개수를 화면에 표시합니다.
        binding.count.text = selectedOrderLists.size.toString()

        // 어댑터 초기화 및 클릭 리스너 설정
        selectedOrderlistAdapter = SelectedOrderlistAdapter(selectedOrderLists)
        selectedOrderlistAdapter.setOnItemClickListener(object : SelectedOrderlistAdapter.OnItemClickListeners {
            override fun onItemClick(binding: SelectedorderlistItemBinding, order: Order, position: Int) {
                // 클릭된 주문 정보를 전달하여 상세 화면으로 이동합니다.
                val intent = Intent(this@SelectedOrderlistActivity, SelectedOrderActivity::class.java).apply {
                    putExtra("selectedOrder", selectedOrderLists[position])
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        // RecyclerView 설정
        binding.myorderlistRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.myorderlistRecyclerView.adapter = selectedOrderlistAdapter
        selectedOrderlistAdapter.notifyDataSetChanged()
    }

    /**
     * 사용자 ID를 통해 주문 정보를 가져오는 함수입니다.
     * @param userId Int - 사용자 ID
     */
    private fun getOrdersByUserId(userId: Int) {
        val call = RetrofitService.orderService.getOrdersByUserId(userId)
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    if (orders != null) {
                        selectedOrderLists.addAll(orders) // 주문 리스트에 데이터 추가
                        Log.d("IncomeChartActivity", "Order added to list: $orders")
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
