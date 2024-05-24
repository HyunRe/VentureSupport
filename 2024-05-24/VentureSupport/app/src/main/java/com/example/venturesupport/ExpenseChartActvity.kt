package com.example.venturesupport

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.venturesupport.databinding.ExpensechartBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

class ExpenseChartActvity: AppCompatActivity() {
    private val binding: ExpensechartBinding by lazy {
        ExpensechartBinding.inflate(layoutInflater)
    }
    /*
    private val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        Log.d("YearMonthPickerTest", "year = $year, month = $monthOfYear, day = $dayOfMonth")
    }
    */
    private lateinit var expenseChartAdapter: ExpenseChartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.dateButton.setOnClickListener {
            val pd = MyYearMonthPickerDialog()
            pd.setListener { _, year, monthOfYear, _ ->
                val dateString = "${year}년.${monthOfYear + 1}월"
                val month = "${monthOfYear + 1}월 지출"
                // 수정 필요
                binding.dateButton.text = dateString
                binding.monthTextView.text = month
            }
            pd.show(supportFragmentManager, "YearMonthPickerTest")
        }

        // 수정 필요
        binding.expenseTextView.text = "345083원"

        // 수정 필요
        val pieChart: PieChart = binding.pieChart
        val dataList = arrayListOf(
            PieEntry(10F, "세금 정산"),
            PieEntry(20F, "수리비"),
            PieEntry(50F, "차량 구매 비"),
            PieEntry(20F, "보험료"),
            PieEntry(50F, "유류비"),
            PieEntry(10F, "톨 게이트 비")
        )

        val dataSet = PieDataSet(dataList, "지츌 내역")
        dataSet.colors = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueFormatter = PercentFormatter(pieChart)

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.setUsePercentValues(true)
        pieChart.invalidate()

        expenseChartAdapter = ExpenseChartAdapter(dataList)
        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.expenseRecyclerView.adapter = expenseChartAdapter
    }
}