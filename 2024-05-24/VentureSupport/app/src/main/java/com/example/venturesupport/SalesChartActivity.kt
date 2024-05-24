package com.example.venturesupport

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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

class SalesChartActivity: AppCompatActivity() {
    private val binding: SaleschartBinding by lazy {
        SaleschartBinding.inflate(layoutInflater)
    }
    /*
    private val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        Log.d("YearMonthPickerTest", "year = $year, month = $monthOfYear, day = $dayOfMonth")
    }
    */
    private lateinit var salesChartAdapter: SalesChartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.yearButton.setOnClickListener {
            val pd = MyYearMonthPickerDialog()
            pd.setListener { _, year, monthOfYear, _ ->
                val year = "${year}년"
                binding.yearButton.text = year
            }
            pd.show(supportFragmentManager, "YearMonthPickerTest")
        }

        // 수정필요
        binding.incomeButton.text = "345083원"
        binding.expenseButton.text = "250267원"

        binding.incomeButton.setOnClickListener {
            val intent = Intent(this, IncomeChartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.expenseButton.setOnClickListener {
            val intent = Intent(this, ExpenseChartActvity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val barChart: BarChart = binding.barchart
        // 데이터 생성
        val entriesBackground = ArrayList<BarEntry>()
        val entriesForeground = ArrayList<BarEntry>()

        // 전체 바 (배경)
        entriesBackground.add(BarEntry(1f, 100f))
        entriesBackground.add(BarEntry(2f, 100f))
        entriesBackground.add(BarEntry(3f, 100f))
        entriesBackground.add(BarEntry(4f, 100f))
        entriesBackground.add(BarEntry(5f, 100f))
        entriesBackground.add(BarEntry(6f, 100f))
        entriesBackground.add(BarEntry(7f, 100f))
        entriesBackground.add(BarEntry(8f, 100f))
        entriesBackground.add(BarEntry(9f, 100f))
        entriesBackground.add(BarEntry(10f, 100f))
        entriesBackground.add(BarEntry(11f, 100f))
        entriesBackground.add(BarEntry(12f, 100f))

        // 수정필요
        // 실제 값 (전경)
        entriesForeground.add(BarEntry(1f, 70f))
        entriesForeground.add(BarEntry(2f, 40f))
        entriesForeground.add(BarEntry(3f, 90f))
        entriesForeground.add(BarEntry(4f, 50f))
        entriesForeground.add(BarEntry(5f, 30f))
        entriesBackground.add(BarEntry(6f, 56f))
        entriesBackground.add(BarEntry(7f, 32f))
        entriesBackground.add(BarEntry(8f, 71f))
        entriesBackground.add(BarEntry(9f, 64f))
        entriesBackground.add(BarEntry(10f, 42f))
        entriesBackground.add(BarEntry(11f, 48f))
        entriesBackground.add(BarEntry(12f, 29f))

        // 데이터셋 생성
        val barDataSetBackground = BarDataSet(entriesBackground, "Background")
        barDataSetBackground.setColor(Color.LTGRAY)

        val barDataSetForeground = BarDataSet(entriesForeground, "Foreground")
        barDataSetForeground.setColors(*ColorTemplate.COLORFUL_COLORS)

        // BarData 생성
        val barData = BarData(barDataSetBackground, barDataSetForeground)
        barData.barWidth = 0.4f // 막대 너비 설정
        barChart.data = barData

        // 막대 간 간격 설정
        barChart.groupBars(0f, 0.08f, 0.03f)

        // X축 라벨 설정
        val labels = arrayOf("Label1", "Label2", "Label3", "Label4", "Label5", "Label6",
            "Label7", "Label8", "Label9", "Label10", "Label11", "Label12")


        barChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return if (value.toInt() >= 0 && value.toInt() < labels.size) {
                    labels[value.toInt()]
                } else {
                    ""
                }
            }
        }

        // X축 위치 설정
        barChart.xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM

        // 차트 갱신
        barChart.invalidate()

        salesChartAdapter = SalesChartAdapter(entriesForeground)
        binding.salesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.salesRecyclerView.adapter = salesChartAdapter
    }
}