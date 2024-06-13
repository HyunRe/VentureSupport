package com.example.venturesupport

import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.venturesupport.databinding.ExpensechartBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExpenseChartActivity : AppCompatActivity() {
    private val binding: ExpensechartBinding by lazy {
        ExpensechartBinding.inflate(layoutInflater)
    }
    private lateinit var expenseChartAdapter: ExpenseChartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // 액티비티의 레이아웃 설정

        // 인텐트를 통해 사용자 ID를 가져옴
        val userId = intent.getIntExtra("USER_ID", -1)
        if (userId == -1) {
            Toast.makeText(this, "유효하지 않은 사용자 ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadPaymentById(userId) // 사용자 ID를 이용해 결제 수단 로드
        loadAllExpenses(userId) // 모든 지출 내역 로드

        // 현재 연도와 월을 가져옴
        val cal = Calendar.getInstance() //캘린더 선언
        val year = cal.get(Calendar.YEAR) //연도값 저장 변수
        val month = cal.get(Calendar.MONTH) + 1 //개월 값 저장 변수

        binding.dateButton.text = "${year}년 ${month}월"
        binding.monthTextView.text = "${month}월 지출"

        // 날짜 선택 버튼 클릭 리스너 설정
        binding.dateButton.setOnClickListener {
            val pd = MyYearMonthPickerDialog() // 날짜 선택 다이얼로그 생성
            pd.setListener { _, year, monthOfYear, _ ->
                val dateString = "${year}년 ${monthOfYear + 1}월" // 선택한 날짜 형식
                val month = "${monthOfYear + 1}월 지출" // 선택한 월
                binding.dateButton.text = dateString
                binding.monthTextView.text = month
                loadAllExpenses(userId, year, monthOfYear + 1) // 선택한 연도와 월의 지출 내역 로드
            }
            pd.show(supportFragmentManager, "YearMonthPickerTest") // 다이얼로그 표시
        }

        // 초기 파이 차트 설정
        setupPieChart(binding.pieChart, emptyList()) // 빈 리스트로 파이 차트 초기화
    }

    // 모든 지출 내역을 로드하는 함수
    private fun loadAllExpenses(userId: Int, year: Int = Calendar.getInstance().get(Calendar.YEAR), month: Int = Calendar.getInstance().get(Calendar.MONTH) + 1) {
        val call = RetrofitService.expenseService.getAllExpenses() // 모든 지출 내역 호출
        call.enqueue(object : Callback<List<Expense>> {
            override fun onResponse(call: Call<List<Expense>>, response: Response<List<Expense>>) {
                if (response.isSuccessful) {
                    val expenses = response.body() // 응답에서 지출 내역 추출
                    expenses?.let {
                        // 사용자 ID와 연도, 월이 일치하는 지출 내역 필터링
                        val filteredExpenses = it.filter { expense ->
                            val cal = Calendar.getInstance().apply { time = expense.expenseDate }
                            expense.user.userId == userId && cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) + 1 == month
                        }
                        updatePieChart(filteredExpenses) // 필터링된 지출 내역을 파이 차트로 업데이트
                        val totalAmount = filteredExpenses.sumOf { expense -> expense.expenseAmount } // 총 지출 금액 계산
                        binding.expenseTextView.text = "${totalAmount}원" // 텍스트뷰에 총 금액 표시
                    }
                } else {
                    Toast.makeText(this@ExpenseChartActivity, "지출 내역 조회 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Expense>>, t: Throwable) {
                Toast.makeText(this@ExpenseChartActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 사용자 ID를 이용해 결제 수단을 로드하는 함수
    private fun loadPaymentById(userId: Int) {
        val call = RetrofitService.paymentService.getPaymentById(userId) // 결제 수단 API 호출
        call.enqueue(object : Callback<Payment> {
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if (response.isSuccessful) {
                    val payment = response.body() // 결제 수단 정보 추출
                    binding.payLongTextView.text = payment?.paymentName ?: "결제 수단 없음" // 텍스트뷰에 결제 수단 표시
                } else {
                    Toast.makeText(this@ExpenseChartActivity, "결제 수단 조회 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Payment>, t: Throwable) {
                Toast.makeText(this@ExpenseChartActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 지출 내역을 이용해 파이 차트를 업데이트하는 함수
    private fun updatePieChart(expenses: List<Expense>) {
        val pieEntries = expenses.map { PieEntry(it.expenseAmount.toFloat(), it.expenseDetails) }
        setupPieChart(binding.pieChart, pieEntries) //파이 차트 초기 설정
    }

    // 파이 차트를 설정하는 함수
    private fun setupPieChart(pieChart: PieChart, dataList: List<PieEntry>) {
        val dataSet = PieDataSet(dataList, "지출 내역") // PieDataSet 생성
        dataSet.colors = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN) // 색상 설정
        dataSet.valueTextSize = 12f // 값 텍스트 크기 설정
        dataSet.valueTextColor = Color.BLACK // 값 텍스트 색상 설정
        dataSet.valueFormatter = PercentFormatter(pieChart) // 퍼센트 포맷터 설정

        val data = PieData(dataSet) // PieData 생성
        pieChart.data = data
        pieChart.setUsePercentValues(true) // 퍼센트 값 사용 설정
        pieChart.invalidate() // 차트 새로 고침

        // 리사이클러뷰 어댑터 설정
        expenseChartAdapter = ExpenseChartAdapter(dataList)
        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.expenseRecyclerView.adapter = expenseChartAdapter
        expenseChartAdapter.notifyDataSetChanged() // 어댑터 데이터 변경 알림
    }
}
