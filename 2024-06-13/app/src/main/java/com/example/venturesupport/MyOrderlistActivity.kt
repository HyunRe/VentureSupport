package com.example.venturesupport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.venturesupport.databinding.MyorderlistBinding
import com.example.venturesupport.databinding.MyorderlistItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**
 * 사용자 주문 목록을 관리하는 액티비티입니다.
 * 주문 목록을 조회하고, 추가, 수정, 삭제 기능을 제공합니다.
 */
class MyOrderlistActivity: AppCompatActivity() {
    private val binding: MyorderlistBinding by lazy {
        MyorderlistBinding.inflate(layoutInflater)
    }
    // 어댑터 초기화
    private lateinit var myOrderlistAadpter: MyOrderlistAadpter

    // 주문 목록 데이터를 저장하는 리스트
    private var myOrderLists = ArrayList<Order>()

    // 현재 화면에 표시할 회사 정보를 저장
    private var company: Company? = null

    // 수정 또는 삭제할 주문 정보를 저장
    private var intentOrder: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 인텐트로부터 회사 및 주문 정보를 받아옵니다.
        @Suppress("DEPRECATION")
        company = intent.getParcelableExtra("intentCompany")
        @Suppress("DEPRECATION")
        intentOrder = intent.getParcelableExtra("intentOrder")

        // 회사 ID를 설정합니다. (회사 또는 주문 정보에서 가져옴)
        val id = company?.companyId ?: intentOrder?.company?.companyId ?: 0

        // 회사 ID를 사용하여 주문 목록을 가져옵니다.
        getOrdersByCompanyId(id)

        // 초기 주문 목록 개수를 화면에 표시합니다.
        binding.count.text = myOrderLists.size.toString()

        // '추가' 버튼 클릭 시 AddProductActivity로 이동합니다.
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            intent.putExtra("intentCompany", company)
            startActivity(intent)
        }

        // 어댑터 초기화 및 클릭 리스너 설정
        myOrderlistAadpter = MyOrderlistAadpter(myOrderLists)
        myOrderlistAadpter.setOnItemClickListener(object : MyOrderlistAadpter.OnItemClickListeners {
            override fun onItemClick(binding: MyorderlistItemBinding, order: Order, position: Int) {
                // 주문 아이템 클릭 시 EditOrderActivity로 이동합니다.
                val intent = Intent(this@MyOrderlistActivity, EditOrderActivity::class.java).apply {
                    putExtra("editOrder", myOrderLists[position])
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            override fun onItemLongClick(binding: MyorderlistItemBinding, order: Order, position: Int) {
                // 주문 아이템 롱 클릭 시 삭제 확인 다이얼로그를 표시합니다.
                val builder = AlertDialog.Builder(this@MyOrderlistActivity)
                builder.setTitle("삭제 확인")
                builder.setMessage("이 아이템을 삭제하시겠습니까?")

                builder.setPositiveButton("삭제") { dialog, which ->
                    myOrderlistAadpter.removeItem(position) // 아이템 삭제
                    val orderId = order.orderId ?: error("Order ID가 null입니다.")
                    deleteOrder(orderId) // 서버에서 주문 삭제 요청
                }

                builder.setNegativeButton("취소") { dialog, which ->
                    dialog.dismiss() // 다이얼로그 닫기
                }

                val dialog = builder.create()
                dialog.show()

            }
        })

        // RecyclerView 설정
        binding.myorderlistRecyclerView.layoutManager = LinearLayoutManager(this)
        // RecyclerView에 어댑터 설정: 데이터 연결 및 표시를 담당합니다.
        binding.myorderlistRecyclerView.adapter = myOrderlistAadpter
        // 어댑터에 데이터 변경 알림: RecyclerView를 갱신하여 변경된 데이터를 반영합니다.
        myOrderlistAadpter.notifyDataSetChanged()
    }

    /**
     * 회사 ID로 주문 목록을 조회하는 메서드입니다.
     */
    private fun getOrdersByCompanyId(companyId: Int) {
        val call = RetrofitService.orderService.getOrdersByCompanyId(companyId)
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    if (orders != null) {
                        myOrderLists.addAll(orders) // 주문 목록 추가
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

    /**
     * 주문을 삭제하는 메서드입니다.
     */
    private fun deleteOrder(orderId: Int) {
        val call = RetrofitService.orderService.deleteOrder(orderId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("CreateOrderActivity", "서버에서 아이템 삭제 성공")
                } else {
                    Log.e("CreateOrderActivity", "서버에서 아이템 삭제 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("CreateOrderActivity", "서버와의 통신 실패", t)
            }
        })
    }
}