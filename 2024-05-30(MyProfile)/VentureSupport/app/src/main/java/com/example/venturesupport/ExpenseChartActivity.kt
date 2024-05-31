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

class ExpenseChartActivity: AppCompatActivity() {
    private val binding: ExpensechartBinding by lazy {
        ExpensechartBinding.inflate(layoutInflater)
    }
    private lateinit var expenseChartAdapter: ExpenseChartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val userId = 1 // Replace with actual user ID
        loadPaymentById(userId)
        loadAllExpenses()

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1

        binding.dateButton.text = "${year}년 ${month}월"
        binding.monthTextView.text = "${month}월 지출"

        binding.dateButton.setOnClickListener {
            val pd = MyYearMonthPickerDialog()
            pd.setListener { _, year, monthOfYear, _ ->
                val dateString = "${year}년 ${monthOfYear}월"
                val month = "${monthOfYear}월 지출"
                binding.dateButton.text = dateString
                binding.monthTextView.text = month
            }
            pd.show(supportFragmentManager, "YearMonthPickerTest")
        }

        // 결제 수단 조회(수정 필요)
        binding.expenseTextView.text = "345083원"

        // 초기 차트 설정
        val dataList = arrayListOf(
            PieEntry(10F, "세금 정산"),
            PieEntry(20F, "수리비"),
            PieEntry(50F, "차량 구매 비"),
            PieEntry(20F, "보험료"),
            PieEntry(50F, "유류비"),
            PieEntry(10F, "톨 게이트 비")
        )
        setupPieChart(binding.pieChart, emptyList())
    }

    private fun loadAllExpenses() {
        val call = RetrofitService.expenseService.getAllExpenses()
        call.enqueue(object : Callback<List<Expense>> {
            override fun onResponse(call: Call<List<Expense>>, response: Response<List<Expense>>) {
                if (response.isSuccessful) {
                    val expenses = response.body()
                    expenses?.let {
                        updatePieChart(it)
                        updateExpenseTextView(it)
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

    private fun updatePieChart(expenses: List<Expense>) {
        val pieEntries = ArrayList(expenses.map { PieEntry(it.expenseAmount.toFloat(), it.expenseDetails) })
        val dataSet = PieDataSet(pieEntries, "지출 내역")
        dataSet.colors = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueFormatter = PercentFormatter(binding.pieChart)

        val data = PieData(dataSet)
        binding.pieChart.data = data
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.invalidate()
    }

    private fun setupPieChart(pieChart: PieChart, dataList: List<Any>) {
        // dataList를 PieEntry 리스트로 형변환하여 전달
        val pieEntryList = dataList.filterIsInstance<PieEntry>()

        val dataSet = PieDataSet(pieEntryList, "지출 내역")
        dataSet.colors = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueFormatter = PercentFormatter(pieChart)

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.setUsePercentValues(true)
        pieChart.invalidate()

        expenseChartAdapter = ExpenseChartAdapter(pieEntryList)
        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.expenseRecyclerView.adapter = expenseChartAdapter
        expenseChartAdapter.notifyDataSetChanged()
    }


    private fun updateExpenseTextView(expenses: List<Expense>) {
        val totalAmount = expenses.sumOf { it.expenseAmount }
        binding.expenseTextView.text = "${totalAmount}원"
    }
}
