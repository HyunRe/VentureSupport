package com.example.venturesupport

import User
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.IncomechartBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

// IncomeChartActivity: 사용자의 수입 내역을 조회하고 시각화하는 액티비티
class IncomeChartActivity : AppCompatActivity() {
    private val binding: IncomechartBinding by lazy {
        IncomechartBinding.inflate(layoutInflater)
    }

    // 수입 내역을 저장하는 리스트
    private var incomeLists = ArrayList<Order>()
    // 사용자 정보 저장
    private var user: User? = null

    // onCreate: 액티비티가 생성될 때 호출되는 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // 뷰 설정

        // 인텐트로부터 사용자 정보 가져오기
        @Suppress("DEPRECATION")
        user = intent.getParcelableExtra("user")

        // 사용자 ID를 이용해 오더 내역을 서버에서 로드
        getOrderByUserId(user?.userId!!)

        // 캘린더뷰에서 날짜가 선택되었을 때의 이벤트 리스너 설정
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            // 선택된 날짜를 포맷하여 텍스트뷰에 설정
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val selectedDate = dateFormat.format(calendar.time)
            binding.dateTextView.text = "선택한 날짜: $selectedDate"

            // 선택된 날짜에 해당하는 오더를 필터링
            val specificDateOrders = incomeLists.filter { dateFormat.format(it.date) == selectedDate }
            // 필터링된 오더의 총 급료 계산
            val totalSalary = specificDateOrders.sumOf { it.salary }
            // 필터링된 오더의 개수 계산
            val orderCount = specificDateOrders.size

            // 계산된 급료와 오더 개수를 텍스트뷰에 설정
            binding.amountTextView.text = "일당: $totalSalary 원"
            binding.countTextView.text = "운송 건수: $orderCount 건"

            // 급료나 오더 개수가 0이 아닐 경우 텍스트뷰의 배경색을 변경
            binding.dateTextView.isSelected = totalSalary != 0.0 || orderCount != 0
        }
    }

    // 사용자 ID를 이용해 서버에서 오더 내역을 로드하는 함수
    private fun getOrderByUserId(userId: Int) {
        val call = RetrofitService.orderService.getOrdersByUserId(userId)
        call.enqueue(object : Callback<List<Order>> {
            // 서버 응답이 성공적일 때 호출되는 함수
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val order = response.body() // 응답으로 받은 오더 리스트
                    if (order != null) {
                        incomeLists.addAll(order) // 받은 오더를 리스트에 추가
                        Log.d("IncomeChartActivity", "Order added to list: $order")
                    } else {
                        Log.e("IncomeChartActivity", "No order data found in response")
                    }
                } else {
                    Log.e("IncomeChartActivity", "Request failed with status: ${response.code()}")
                }
            }

            // 네트워크 요청이 실패했을 때 호출되는 함수
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("IncomeChartActivity", "Network request failed", t)
            }
        })
    }
}
