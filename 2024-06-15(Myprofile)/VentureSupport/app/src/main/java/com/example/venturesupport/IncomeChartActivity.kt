package com.example.venturesupport

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.ParseException
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
    private var intentUser: User? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // 뷰 설정

        intentUser = intent.getParcelableExtra("intentUser")

        getOrderByUserId(intentUser?.userId!!)

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            // 선택된 날짜를 포맷하여 텍스트뷰에 설정
            val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            val selectedDate = dateFormat.format(calendar.time)
            binding.dateTextView.text = "선택한 날짜: $selectedDate"

            // 선택된 날짜에 해당하는 오더를 필터링
            val specificDateOrders = incomeLists.filter {
                try {
                    // 날짜 문자열을 Date 객체로 파싱합니다.
                    val orderDate = dateFormat.parse(it.date)
                    // 파싱한 Date 객체를 다시 문자열로 변환한 후 selectedDate와 비교합니다.
                    dateFormat.format(orderDate) == selectedDate
                } catch (e: ParseException) {
                    false
                }
            }
            // 필터링된 오더의 총 급료 계산
            val totalSalary = specificDateOrders.sumOf { it.salary }
            // 필터링된 오더의 개수 계산
            val orderCount = specificDateOrders.size

            // 계산된 급료와 오더 개수를 텍스트뷰에 설정
            binding.amountTextView.text = "일당: $totalSalary 원"
            binding.countTextView.text = "운송 건수: $orderCount 건"

            binding.dateTextView.isSelected = totalSalary != 0 || orderCount != 0
        }
    }

    private fun getOrderByUserId(userId: Int) {
        val call = RetrofitService.orderService.getOrderByUserId(userId)
        call.enqueue(object : Callback<List<Order>> {
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
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("IncomeChartActivity", "Network request failed", t)
            }
        })
    }
}
