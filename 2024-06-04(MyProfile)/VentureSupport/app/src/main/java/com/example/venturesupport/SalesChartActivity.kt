package com.example.venturesupport

import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.venturesupport.RetrofitService.orderService
import com.example.venturesupport.databinding.SaleschartBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 매출 차트를 표시하는 액티비티.
 * 특정 사용자의 결제 및 주문 정보를 로드하고 월별 매출을 막대 차트로 시각화한다.
 */
class SalesChartActivity : AppCompatActivity() {
    // View binding 인스턴스
    private val binding: SaleschartBinding by lazy {
        SaleschartBinding.inflate(layoutInflater)
    }

    // 매출 차트를 표시하기 위한 어댑터
    private lateinit var salesChartAdapter: SalesChartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 현재 연도를 가져오기 위한 캘린더 인스턴스
        val cal = Calendar.getInstance() 
        val year = cal.get(Calendar.YEAR)

        // 특정 사용자의 결제 및 주문 정보를 로드
        val userId = intent.getIntExtra("USER_ID", -1)
        if (userId == -1) {
            Toast.makeText(this, "유효하지 않은 사용자 ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        loadPaymentById(userId) //결제 정보 관련
        loadOrdersByUserId(userId) //주문 정보 관련

        // 차트에 초기 연도 설정
        binding.yearButton.text = "${year}년"

        // 연도 선택 버튼 클릭 처리
        binding.yearButton.setOnClickListener {
            val pd = MyYearPickerDialog()
            pd.setListener { _, year, _, _ ->
                val year = "${year}년"
                binding.yearButton.text = year
            }
            pd.show(supportFragmentManager, "YearPickerTest")
        }

        // 초기값 설정: 수입 및 지출 버튼
        binding.incomeButton.text = "345083원"
        binding.expenseButton.text = "250267원"

        // 수입 버튼 클릭 처리
        binding.incomeButton.setOnClickListener {
            val intent = Intent(this, IncomeChartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        // 지출 버튼 클릭 처리
        binding.expenseButton.setOnClickListener {
            val intent = Intent(this, ExpenseChartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        // 막대 차트 초기화 및 설정
        // BarChart 인스턴스 생성 및 초기화
        val barChart: BarChart = binding.barchart

        // 막대 차트에 표시될 데이터를 저장하는 ArrayList
        val entriesForeground = ArrayList<BarEntry>()

        // 막대 차트에 대한 데이터셋 생성
        val barDataSetForeground = BarDataSet(entriesForeground, "Foreground")
        barDataSetForeground.setColors(*ColorTemplate.COLORFUL_COLORS) // 막대 색상 설정

        // 막대 차트에 표시할 데이터를 설정한 데이터셋으로부터 BarData 생성
        val barData = BarData(barDataSetForeground)
        barData.barWidth = 0.4f // 막대 너비 설정

        // BarData를 막대 차트에 설정
        barChart.data = barData

        /*먼저 사용할 라벨을 배열로 정의하고, 그 후에는 labelCount를 사용하여 라벨 개수를 설정.
        setGranularity는 간격을 설정하는데, 여기서는 각 라벨 사이의 간격을 1로 설정.
        그리고 valueFormatter를 사용하여 X축 값의 포맷을 설정.
        이 때, ValueFormatter의 getAxisLabel 함수를 오버라이드하여 실제 값에 해당하는 라벨을 반환. 마지막으로 X축의 위치를 설정*/

        // X축에 표시될 라벨 배열
        val labels = arrayOf(
            "1월", "2월", "3월", "4월", "5월", "6월",
            "7월", "8월", "9월", "10월", "11월", "12월"
        )

        // 막대 차트의 X축 라벨 개수 설정
        barChart.xAxis.labelCount = labels.size
        // 막대 차트의 X축 간격 설정
        barChart.xAxis.setGranularity(1f)
        // 막대 차트의 X축 값 포맷 설정
        barChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return if (value.toInt() >= 0 && value.toInt() < labels.size) {
                    labels[value.toInt()]
                } else {
                    ""
                }
            }
        }
        // 막대 차트의 X축 위치 설정
        barChart.xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
        // 막대 차트의 X축 간격 설정
        barChart.xAxis.setGranularity(1f)


        // 매출 차트 어댑터 초기화 및 RecyclerView에 레이아웃 매니저 설정
        salesChartAdapter = SalesChartAdapter(entriesForeground)
        binding.salesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.salesRecyclerView.adapter = salesChartAdapter
        salesChartAdapter.notifyDataSetChanged()
    }

    
    // 특정 사용자의 결제 정보를 로드합니다.
    private fun loadPaymentById(userId: Int) {
        // Retrofit을 사용하여 서버로부터 해당 사용자의 결제 정보를 요청합니다.
        val call = RetrofitService.paymentService.getPaymentById(userId)
        call.enqueue(object : Callback<Payment> {
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                // 서버 응답이 성공적으로 수신된 경우
                if (response.isSuccessful) {
                    val payment = response.body()
                    // 결제 정보를 화면에 표시합니다.
                    binding.payLongTextView.text = payment?.paymentName ?: "결제 수단 없음"
                } else {
                    // 서버 응답이 실패한 경우 에러 메시지를 사용자에게 표시합니다.
                    Toast.makeText(this@SalesChartActivity, "결제 수단 조회 실패", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Payment>, t: Throwable) {
                // 네트워크 오류가 발생한 경우 에러 메시지를 사용자에게 표시합니다.
                Toast.makeText(this@SalesChartActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 특정 사용자의 주문 정보를 로드합니다.
    private fun loadOrdersByUserId(userId: Int) {
        // Retrofit을 사용하여 서버로부터 해당 사용자의 주문 정보를 요청합니다.
        val call = RetrofitService.orderService.getOrderByUserId(userId)
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                // 서버 응답이 성공적으로 수신된 경우
                if (response.isSuccessful) {
                    val orders = response.body()
                    orders?.let {
                        // 막대 차트와 판매 정보를 업데이트합니다.
                        updateBarChart(it)
                        updateSalesTextView(it)
                    }
                } else {
                    // 서버 응답이 실패한 경우 에러 메시지를 사용자에게 표시합니다.
                    Toast.makeText(this@SalesChartActivity, "주문 내역 조회 실패", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                // 네트워크 오류가 발생한 경우 에러 메시지를 사용자에게 표시합니다.
                Toast.makeText(this@SalesChartActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 막대 차트를 업데이트합니다.
    private fun updateBarChart(orders: List<Order>) {
        val entriesForeground = ArrayList<BarEntry>()
        val monthlyTotals = FloatArray(12) { 0f }

        // 각 월별 매출 합계를 계산합니다.
        for (order in orders) {
            val calendar = Calendar.getInstance().apply {
                time = order.date
            }
            val month = calendar.get(Calendar.MONTH)
            monthlyTotals[month] += order.totalAmount.toFloat()
        }

        // 막대 차트에 표시될 데이터를 추가합니다.
        for (i in monthlyTotals.indices) {
            entriesForeground.add(BarEntry((i + 1).toFloat(), monthlyTotals[i]))
        }

        // 막대 차트에 데이터를 설정하고 차트를 갱신합니다.
        val barDataSetForeground = BarDataSet(entriesForeground, "Sales")
        barDataSetForeground.setColors(*ColorTemplate.COLORFUL_COLORS)

        val barData = BarData(barDataSetForeground)
        barData.barWidth = 0.4f

        binding.barchart.data = barData
        binding.barchart.invalidate() // 차트를 새로 고칩니다.
    }

    // 판매 정보를 업데이트합니다.
    private fun updateSalesTextView(orders: List<Order>) {
        var totalIncome = 0
        var totalExpense = 0

        // 총 수입과 지출을 계산합니다.
        for (order in orders) {
            totalIncome += order.totalAmount
            totalExpense += order.salary.toInt()
        }

        // 화면에 수입과 지출을 표시합니다.
        binding.incomeButton.text = "${totalIncome}원"
        binding.expenseButton.text = "${totalExpense}원"
    }
}
