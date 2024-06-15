package com.example.venturesupport

import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
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
    private var expenseLists = ArrayList<Expense>()
    private var intentUser: User? = null
    private var year: Int = 0
    private var monthOfYear: Int = 0

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentUser = intent.getParcelableExtra("intentUser")

        val cal = Calendar.getInstance()
        year = cal.get(Calendar.YEAR)
        monthOfYear = cal.get(Calendar.MONTH)

        binding.dateButton.setOnClickListener {
            val pd = MyYearMonthPickerDialog()
            pd.setListener { _, pickedYear, pickedMonthOfYear, _ ->
                year = pickedYear
                monthOfYear = pickedMonthOfYear

                val dateString = "${year}년 ${monthOfYear + 1}월" // monthOfYear는 0부터 시작하므로 +1
                val month = "${monthOfYear + 1}월 지출"
                binding.dateButton.text = dateString
                binding.monthTextView.text = month
                getExpensesByUserId(intentUser?.userId!!)
            }
            pd.show(supportFragmentManager, "YearMonthPickerTest")
        }
    }

    private fun getExpensesByUserId(userId: Int) {
        val call = RetrofitService.expenseService.getExpensesByUserId(userId)
        call.enqueue(object : Callback<List<Expense>> {
            override fun onResponse(call: Call<List<Expense>>, response: Response<List<Expense>>) {
                if (response.isSuccessful) {
                    val expenses = response.body()
                    if (expenses != null) {
                        Log.d("ExpenseActivity", "Expenses: $expenses")
                        val filteredExpenses = expenses.filter { expense ->
                            val expenseDate = expense.expenseDate // 예시: "2024.06.13"
                            val selectedYear = year // 선택된 연도
                            val selectedMonth = monthOfYear + 1 // 선택된 월

                            // 예시에서 "2024", "06" 추출
                            val expenseYear = expenseDate.split(".")[0].toInt()
                            val expenseMonth = expenseDate.split(".")[1].toInt()

                            expenseYear == selectedYear && expenseMonth == selectedMonth
                        }

                        expenseLists.addAll(filteredExpenses)
                        val totalAmount = filteredExpenses.map { it.expenseAmount } // 각 expense의 expenseAmount 추출
                            .reduceOrNull { acc, amount -> acc + amount } ?: 0 // 모든 expenseAmount 합산
                        binding.expenseTextView.text = totalAmount.toString()
                        setupPieChart(binding.pieChart, convertExpenseListToPieEntryList(expenseLists))
                        expenseChartAdapter.notifyDataSetChanged()
                    } else {
                        Log.e("ExpenseActivity", "No expense data found in response")
                    }
                } else {
                    Log.e("ExpenseActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Expense>>, t: Throwable) {
                Log.e("ExpenseActivity", "Network request failed", t)
            }
        })
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
    }

    private fun convertExpenseListToPieEntryList(expenses: List<Expense>): List<PieEntry> {
        val pieEntries = mutableListOf<PieEntry>()
        for (expense in expenses) {
            // Example conversion, modify according to your Expense class
            val pieEntry = PieEntry(expense.expenseAmount.toFloat(), expense.expenseDetails)
            pieEntries.add(pieEntry)
        }
        return pieEntries
    }
}
