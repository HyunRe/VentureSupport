package com.example.venturesupport

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

class SalesChartActivity : AppCompatActivity() {
    private val binding: SaleschartBinding by lazy {
        SaleschartBinding.inflate(layoutInflater)
    }
    private lateinit var salesChartAdapter: SalesChartAdapter
    private var incomeLists = ArrayList<Order>()
    private var expenseLists = ArrayList<Expense>()
    private var salesLists = ArrayList<Sales>()
    private var intentUser: User? = null
    private var year: Int = 0
    private var totalSalary: Int = 0
    private var totalAmount: Int = 0

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentUser = intent.getParcelableExtra("intentUser")

        val cal = Calendar.getInstance()
        year = cal.get(Calendar.YEAR)

        binding.yearButton.setOnClickListener {
            val pd = MyYearPickerDialog()
            pd.setListener { _, pickedYear, _, _ ->
                year = pickedYear

                val selectedYear = "${year}년"
                binding.yearButton.text = selectedYear

                getOrderByUserId(intentUser?.userId!!)
                getExpensesByUserId(intentUser?.userId!!)
            }
            pd.show(supportFragmentManager, "YearMonthPickerTest")
        }

        binding.incomeButton.setOnClickListener {
            val intent = Intent(this, IncomeChartActivity::class.java)
            intent.putExtra("intentUser", intentUser)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.expenseButton.setOnClickListener {
            val intent = Intent(this, ExpenseChartActivity::class.java)
            intent.putExtra("intentUser", intentUser)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val barChart: BarChart = binding.barchart

        val labels = arrayOf(
            "1월", "2월", "3월", "4월", "5월", "6월",
            "7월", "8월", "9월", "10월", "11월", "12월"
        )

        barChart.xAxis.labelCount = labels.size
        barChart.xAxis.setGranularity(1f)
        barChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return if (value.toInt() >= 0 && value.toInt() < labels.size) {
                    labels[value.toInt()]
                } else {
                    ""
                }
            }
        }
        barChart.xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.setGranularity(1f)

        salesChartAdapter = SalesChartAdapter(salesLists)
        binding.salesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.salesRecyclerView.adapter = salesChartAdapter
    }

    private fun getOrderByUserId(userId: Int) {
        val call = RetrofitService.orderService.getOrderByUserId(userId)
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body() ?: return
                    val filteredOrders = orders.filter { it.date.split(".")[0].toInt() == year }
                    incomeLists.clear()
                    incomeLists.addAll(filteredOrders)

                    updateSalesLists()
                    updateChart()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("IncomeChartActivity", "Network request failed", t)
            }
        })
    }

    private fun getExpensesByUserId(userId: Int) {
        val call = RetrofitService.expenseService.getExpensesByUserId(userId)
        call.enqueue(object : Callback<List<Expense>> {
            override fun onResponse(call: Call<List<Expense>>, response: Response<List<Expense>>) {
                if (response.isSuccessful) {
                    val expenses = response.body() ?: return
                    val filteredExpenses = expenses.filter { it.expenseDate.split(".")[0].toInt() == year }
                    expenseLists.clear()
                    expenseLists.addAll(filteredExpenses)

                    updateSalesLists()
                    updateChart()
                }
            }

            override fun onFailure(call: Call<List<Expense>>, t: Throwable) {
                Log.e("ExpenseActivity", "Network request failed", t)
            }
        })
    }

    private fun updateSalesLists() {
        val monthlyIncome = IntArray(12) { 0 }
        val monthlyExpenses = IntArray(12) { 0 }

        incomeLists.forEach { order ->
            val month = order.date.split(".")[1].toInt() - 1
            monthlyIncome[month] += order.salary
        }

        expenseLists.forEach { expense ->
            val month = expense.expenseDate.split(".")[1].toInt() - 1
            monthlyExpenses[month] += expense.expenseAmount
        }

        salesLists.clear()
        for (i in 0..11) {
            salesLists.add(Sales(i + 1, monthlyIncome[i], monthlyExpenses[i]))
        }

        salesChartAdapter.notifyDataSetChanged()
    }

    private fun updateChart() {
        val entries = salesLists.map { BarEntry((it.month - 1).toFloat(), (it.income - it.expense).toFloat()) }
        val barDataSet = BarDataSet(entries, "Sales Data")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val barData = BarData(barDataSet)
        barData.barWidth = 0.4f
        binding.barchart.data = barData
        binding.barchart.invalidate()
    }
}

