package com.example.venturesupport

import Order
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

class IncomeChartActivity : AppCompatActivity() {
    private val binding: IncomechartBinding by lazy {
        IncomechartBinding.inflate(layoutInflater)
    }
    private var incomeLists = ArrayList<Order>()
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        user = intent.getParcelableExtra("user")

        getOrderByUserId(user?.userId!!)

        // 오더 조회 -> 급료 선정 후 총합 & 해당 사용자의 오더 조회 -> 건수
        // CalendarView의 날짜가 선택되었을 때 이벤트 리스너 설정
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            // 선택된 날짜에 대한 텍스트 설정
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val selectedDate = dateFormat.format(calendar.time)
            binding.dateTextView.text = "선택한 날짜: $selectedDate"

            // 선택된 날짜에 해당하는 금액과 count 정보 가져오기 (임의의 예시 데이터 사용)
            val specificDateOrders = incomeLists.filter { dateFormat.format(it.date) == selectedDate }
            val totalSalary = specificDateOrders.sumOf { it.salary }
            val orderCount = specificDateOrders.size

            // 가져온 금액과 count 정보를 TextView에 설정
            binding.amountTextView.text = "일당: $totalSalary 원"
            binding.countTextView.text = "운송 건수: $orderCount 건"

            // 텍스트가 있는지 확인하여 배경색 업데이트
            binding.dateTextView.isSelected = totalSalary != 0.0 || orderCount != 0
        }
    }

    private fun getOrderByUserId(userId: Int) {
        val call = RetrofitService.orderService.getOrderByUserId(userId)
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val order = response.body()
                    if (order != null) {
                        incomeLists.addAll(order)
                        Log.d("IncomeChartActivity", "Order added to list: $order")
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