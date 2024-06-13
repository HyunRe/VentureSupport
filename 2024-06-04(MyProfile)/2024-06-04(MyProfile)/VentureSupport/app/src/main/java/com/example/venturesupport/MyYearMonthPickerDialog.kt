package com.example.venturesupport

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.venturesupport.databinding.YearMonthPickerBinding
import java.util.*

class MyYearMonthPickerDialog : DialogFragment() {
    // View 바인딩을 위한 Lazy 초기화
    private val binding: YearMonthPickerBinding by lazy {
        YearMonthPickerBinding.inflate(layoutInflater)
    }

    // 최대 및 최소 연도 상수
    private val MAX_YEAR = 2099
    private val MIN_YEAR = 1980

    // DatePickerDialog 리스너
    private var listener: DatePickerDialog.OnDateSetListener? = null

    // 캘린더 인스턴스
    private val cal = Calendar.getInstance()

    // DatePickerDialog 리스너 설정 메서드
    fun setListener(listener: DatePickerDialog.OnDateSetListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // AlertDialog.Builder 생성
        val builder = AlertDialog.Builder(requireActivity())

        // 다이얼로그 뷰 초기화
        val dialogView = binding.root

        // 버튼 클릭 리스너 설정
        with(binding) {
            btnCancel.setOnClickListener {
                dialog?.cancel()
            }

            btnConfirm.setOnClickListener {
                // 선택한 연도 및 월 정보를 DatePickerDialog 리스너에 전달
                listener?.onDateSet(null, pickerYear.value, pickerMonth.value, 0)
                dialog?.cancel()
            }

            // 월 피커 설정
            pickerMonth.minValue = 1
            pickerMonth.maxValue = 12
            pickerMonth.value = cal.get(Calendar.MONTH) + 1

            // 현재 연도 설정
            val year = cal.get(Calendar.YEAR)
            pickerYear.minValue = MIN_YEAR
            pickerYear.maxValue = MAX_YEAR
            pickerYear.value = year
        }

        // 다이얼로그에 뷰 설정
        builder.setView(dialogView)

        // AlertDialog 생성하여 반환
        return builder.create()
    }
}
