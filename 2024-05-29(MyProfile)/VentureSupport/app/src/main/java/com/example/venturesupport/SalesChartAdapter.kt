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
        fun bind(barEntry: BarEntry) {
            binding.monthText.text = "5월"
            binding.incomeAmount.text = "345083원"
            binding.expenseAmount.text = "250267원"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SaleschartItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entriesForeground[position])
    }

    override fun getItemCount(): Int {
        return entriesForeground.size
    }
}