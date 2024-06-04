package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.SaleschartItemBinding
import com.github.mikephil.charting.data.BarEntry

/**
 * 판매 차트 항목을 표시하는 RecyclerView에 대한 어댑터입니다.
 * @param entriesForeground 각 월의 판매 데이터를 나타내는 BarEntry 객체의 목록입니다.
 */
class SalesChartAdapter(private val entriesForeground: ArrayList<BarEntry>) : RecyclerView.Adapter<SalesChartAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: SaleschartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * 데이터를 ViewHolder에 바인딩합니다.
         * @param barEntry 특정 월의 판매 데이터를 나타내는 BarEntry 객체입니다.
         * @param month 해당 월을 나타내는 문자열입니다.
         * @param income 월별 수입 데이터를 나타내는 문자열입니다.
         * @param expense 월별 지출 데이터를 나타내는 문자열입니다.
         */
        fun bind(barEntry: BarEntry, month: String, income: String, expense: String) {
            // 월 텍스트 설정
            binding.monthText.text = month //"5월"
            // 수입 금액 텍스트 설정
            binding.incomeAmount.text = income//"345083원"
            // 지출 금액 텍스트 설정
            binding.expenseAmount.text = expense//"250267원"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 항목 레이아웃을 인플레이트합니다.
        val inflater = LayoutInflater.from(parent.context)
        val binding = SaleschartItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 현재 위치의 BarEntry 객체를 가져옵니다.
        val barEntry = entriesForeground[position]
        // 월 텍스트를 생성합니다 (예: "5월")
        val month = "${position + 1}월" // 실제 데이터와 일치시킵니다.
        // 수입 데이터를 위한 자리 표시자
        val income = "수입 데이터" // 실제 수입 데이터를 위한 자리 표시자입니다.
        // 지출 데이터를 위한 자리 표시자
        val expense = "지출 데이터" // 실제 지출 데이터를 위한 자리 표시자입니다.
        // 데이터를 ViewHolder에 바인딩합니다.
        holder.bind(barEntry, month, income, expense)
    }

    override fun getItemCount(): Int {
        // 목록의 항목 수를 반환합니다.
        return entriesForeground.size
    }
}
