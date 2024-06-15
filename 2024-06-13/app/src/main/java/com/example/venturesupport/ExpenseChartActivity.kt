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

// ExpenseChartActivity 클래스: 사용자의 지출 내역을 파이 차트로 보여주는 액티비티
class ExpenseChartActivity : AppCompatActivity() {
    // binding 변수: 레이아웃의 뷰를 바인딩하여 UI 요소에 접근하기 위해 사용
    private val binding: ExpensechartBinding by lazy {
        ExpensechartBinding.inflate(layoutInflater)
    }
    // expenseChartAdapter 변수: 리사이클러뷰의 어댑터를 설정하기 위해 사용
    private lateinit var expenseChartAdapter: ExpenseChartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // 액티비티의 뷰를 설정

        // 인텐트를 통해 사용자 ID를 가져옴
        val userId = intent.getIntExtra("USER_ID", -1)
        if (userId == -1) {
            Toast.makeText(this, "유효하지 않은 사용자 ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadPaymentById(userId) // 사용자 ID를 이용해 결제 수단 로드
        loadAllExpenses(userId) // 모든 지출 내역 로드

        // 현재 연도와 월을 가져와 설정
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1

        // 버튼과 텍스트뷰에 날짜 설정
        binding.dateButton.text = "${year}년 ${month}월"
        binding.monthTextView.text = "${month}월 지출"

        // 날짜 선택 버튼 클릭 리스너 설정
        binding.dateButton.setOnClickListener {
            val pd = MyYearMonthPickerDialog()
            pd.setListener { _, year, monthOfYear, _ ->
                val dateString = "${year}년 ${monthOfYear + 1}월" // monthOfYear는 0부터 시작하므로 +1
                val month = "${monthOfYear + 1}월 지출"
                binding.dateButton.text = dateString
                binding.monthTextView.text = month
                loadAllExpenses(userId, year, monthOfYear + 1) // 선택한 연도와 월의 지출 내역 로드
            }
            pd.show(supportFragmentManager, "YearMonthPickerTest")
        }

        // 초기 파이 차트 설정
        setupPieChart(binding.pieChart, emptyList())
    }

    // 모든 지출 내역을 로드하는 함수
    private fun loadAllExpenses(userId: Int, year: Int = Calendar.getInstance().get(Calendar.YEAR), month: Int = Calendar.getInstance().get(Calendar.MONTH) + 1) {
        //val call = RetrofitService.expenseService.getExpensesByUserAndMonth(userId, year, month)
        val call = RetrofitService.expenseService.getAllExpenses()
        call.enqueue(object : Callback<List<Expense>> {
            override fun onResponse(call: Call<List<Expense>>, response: Response<List<Expense>>) {
                if (response.isSuccessful) {
                    /*val expenses = response.body()
                    expenses?.let {
                        updatePieChart(it) // 지출 내역을 이용해 파이 차트 업데이트
                        // 지출 내역을 이용해 텍스트뷰 업데이트
                        val totalAmount = expenses.sumOf { it.expenseAmount }
                        binding.expenseTextView.text = "${totalAmount}원"
                    }*/
                        val expenses = response.body()
                        expenses?.let {
                            val filteredExpenses = it.filter { expense ->
                                val cal = Calendar.getInstance().apply { time = expense.expenseDate }
                                expense.user.userId == userId && cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) + 1 == month
                            }
                            updatePieChart(filteredExpenses)
                            val totalAmount = filteredExpenses.sumOf { expense -> expense.expenseAmount }
                            binding.expenseTextView.text = "${totalAmount}원"
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
        val call = RetrofitService.paymentService.getPaymentById(userId)
        call.enqueue(object : Callback<Payment> {
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if (response.isSuccessful) {
                    val payment = response.body()
                    binding.payLongTextView.text = payment?.paymentName ?: "결제 수단 없음"
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
        val pieEntries = ArrayList(expenses.map { PieEntry(it.expenseAmount.toFloat(), it.expenseDetails) })
        val dataSet = PieDataSet(pieEntries, "지출 내역")
        dataSet.colors = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN)
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueFormatter = PercentFormatter(binding.pieChart)

        val data = PieData(dataSet)
        binding.pieChart.data = data
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.invalidate()
    }

    // 파이 차트를 설정하는 함수
    private fun setupPieChart(pieChart: PieChart, dataList: List<PieEntry>) {
        val dataSet = PieDataSet(dataList, "지출 내역")
        dataSet.colors = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN)
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueFormatter = PercentFormatter(pieChart)

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.setUsePercentValues(true)
        pieChart.invalidate()

        expenseChartAdapter = ExpenseChartAdapter(dataList)
        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.expenseRecyclerView.adapter = expenseChartAdapter
        expenseChartAdapter.notifyDataSetChanged()
    }
}
