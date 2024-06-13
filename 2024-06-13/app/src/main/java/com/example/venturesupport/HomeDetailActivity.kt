package com.example.venturesupport

import User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.venturesupport.databinding.HomedetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 홈 상세 화면을 나타내는 Activity입니다.
 * 주문 상세 정보를 표시하고, 주문을 수락하거나 취소할 수 있습니다.
 */
class HomeDetailActivity : AppCompatActivity() {

    // ViewBinding 객체 초기화. lazy를 사용하여 레이아웃을 inflate합니다.
    private val binding: HomedetailBinding by lazy {
        HomedetailBinding.inflate(layoutInflater)
    }

    // HomeDetailAdapter 객체 선언
    private lateinit var homeDetailAdapter: HomeDetailAdapter

    // 제품 주문 리스트를 저장할 ArrayList 선언 및 초기화
    private var productOrderLists = ArrayList<Product>()

    // 인텐트로 전달받은 Order 객체와 User 객체를 저장할 변수 선언
    private var intentOrder: Order? = null
    private var intentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // binding 객체의 root 뷰를 setContentView로 설정

        // 인텐트에서 주문 및 사용자 정보를 추출하여 변수에 저장
        @Suppress("DEPRECATION")
        intentOrder = intent.getParcelableExtra("order")
        @Suppress("DEPRECATION")
        intentUser = intent.getParcelableExtra("intentUser")

        // 주문 정보를 화면에 표시
        binding.date.text = intentOrder?.date.toString() // 주문 날짜
        binding.SupplierName.text = intentOrder?.supplier?.supplierName // 공급자 이름
        binding.SupplierPhoneNumber.text = intentOrder?.supplier?.supplierPhoneNumber // 공급자 전화번호
        binding.SupplierLocation.text = intentOrder?.supplier?.supplierLocation // 공급자 위치
        binding.Salary.text = intentOrder?.salary.toString() // 급여
        binding.TotalAmount.text = intentOrder?.totalAmount.toString() // 총 금액

        // 주문 제품 리스트에 제품 추가
        productOrderLists.add(intentOrder?.product!!)

        // 어댑터를 초기화하고 RecyclerView에 설정
        homeDetailAdapter = HomeDetailAdapter(productOrderLists)
        binding.productRecyclerView.layoutManager = LinearLayoutManager(this) // RecyclerView의 레이아웃 매니저 설정
        binding.productRecyclerView.adapter = homeDetailAdapter // 어댑터 설정
        homeDetailAdapter.notifyDataSetChanged() // 데이터 변경을 어댑터에 알림

        // 수락 버튼 클릭 리스너 설정
        binding.acceptButton.setOnClickListener {
            // 주문에 사용자 정보가 없을 경우, 현재 사용자 정보 추가
            if (intentOrder?.user == null) {
                intentOrder?.user = intentUser
                updateOrder(intentOrder!!) // 주문 업데이트 요청
            }
            // HomeActivity로 이동
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish() // 현재 Activity 종료
        }

        // 취소 버튼 클릭 리스너 설정
        binding.cancelButton.setOnClickListener {
            // HomeActivity로 이동
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish() // 현재 Activity 종료
        }
    }

    /**
     * 주문 업데이트를 서버에 요청하는 메서드입니다.
     * @param order Order - 업데이트할 주문 객체
     */
    private fun updateOrder(order: Order) {
        // Retrofit을 사용하여 주문 업데이트 요청
        val call = RetrofitService.orderService.updateOrder(intentOrder?.orderId!!, order)
        call.enqueue(object : Callback<Order> {
            // 네트워크 요청 성공 시 호출되는 메서드
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val updatedOrder = response.body() // 서버 응답에서 업데이트된 주문 객체를 가져옴
                    if (updatedOrder != null) {
                        // 업데이트된 주문 처리
                        Log.d("OrderActivity", "Order updated successfully: $updatedOrder")
                    } else {
                        Log.e("OrderActivity", "Failed to update order: No response body")
                    }
                } else {
                    Log.e("OrderActivity", "Failed to update order: ${response.code()}")
                }
            }

            // 네트워크 요청 실패 시 호출되는 메서드
            override fun onFailure(call: Call<Order>, t: Throwable) {
                Log.e("OrderActivity", "Network request failed", t)
            }
        })
    }
}
