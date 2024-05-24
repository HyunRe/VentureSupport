package com.example.venturesupport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.IncomechartBinding
import java.text.SimpleDateFormat
import java.util.*

class IncomeChartActivity : AppCompatActivity() {
    private val binding: IncomechartBinding by lazy {
        IncomechartBinding.inflate(layoutInflater)
    }
    private var incomeLists = ArrayList<Income>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // CalendarView의 날짜가 선택되었을 때 이벤트 리스너 설정
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            // 선택된 날짜에 대한 텍스트 설정
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val selectedDate = dateFormat.format(calendar.time)
            binding.dateTextView.text = "선택한 날짜: $selectedDate"

            // 선택된 날짜에 해당하는 금액과 count 정보 가져오기 (임의의 예시 데이터 사용)
            val amount = getAmountForDate(year, month, dayOfMonth)
            val count = getCountForDate(year, month, dayOfMonth)

            // 가져온 금액과 count 정보를 TextView에 설정
            binding.amountTextView.text = "일당: $amount 원"
            binding.countTextView.text = "운송 건수: $count 건"

            // 텍스트가 있는지 확인하여 배경색 업데이트
            binding.dateTextView.isSelected = amount != 0.0 || count != 0
        }
    }

    // 수정 필요
    // 선택된 날짜에 대한 금액을 가져오는 함수 (임의의 예시 데이터 사용)
    private fun getAmountForDate(year: Int, month: Int, dayOfMonth: Int): Double {
        // 여기에 실제로 사용하는 데이터 소스나 로직을 구현하세요.
        // 이 예시에서는 임의의 값인 100.0을 반환합니다.
        return 100.0
    }

    // 수정 필요
    // 선택된 날짜에 대한 count를 가져오는 함수 (임의의 예시 데이터 사용)
    private fun getCountForDate(year: Int, month: Int, dayOfMonth: Int): Int {
        // 여기에 실제로 사용하는 데이터 소스나 로직을 구현하세요.
        // 이 예시에서는 임의의 값인 5를 반환합니다.
        return 5
    }
}