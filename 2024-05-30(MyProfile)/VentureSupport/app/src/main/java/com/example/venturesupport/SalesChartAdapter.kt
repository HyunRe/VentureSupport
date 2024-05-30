package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.SaleschartItemBinding
import com.github.mikephil.charting.data.BarEntry

class SalesChartAdapter(private val entriesForeground: ArrayList<BarEntry>) : RecyclerView.Adapter<SalesChartAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: SaleschartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // 수정 필요
        fun bind(barEntry: BarEntry, month: String, income: String, expense: String) {
            binding.monthText.text = month //"5월"
            binding.incomeAmount.text = income//"345083원"
            binding.expenseAmount.text = expense//"250267원"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SaleschartItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val barEntry = entriesForeground[position]
        val month = "${position + 1}월" // 실제 데이터와 매칭되는 월 정보
        val income = "수입 데이터" // 실제 데이터에서 가져온 수입 정보
        val expense = "지출 데이터" // 실제 데이터에서 가져온 지출 정보
        holder.bind(barEntry, month, income, expense)
        //holder.bind(entriesForeground[position])
    }

    override fun getItemCount(): Int {
        return entriesForeground.size
    }
}