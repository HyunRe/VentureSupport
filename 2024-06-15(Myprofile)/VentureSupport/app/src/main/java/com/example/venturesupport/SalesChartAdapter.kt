package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.SaleschartItemBinding

class SalesChartAdapter(private val salesDataList: ArrayList<Sales>) : RecyclerView.Adapter<SalesChartAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SaleschartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sales: Sales) {
            val month = "${sales.month}월"
            val income = "${sales.income}원"
            val expense = "${sales.expense}원"

            binding.monthText.text = month
            binding.incomeAmount.text = income
            binding.expenseAmount.text = expense
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SaleschartItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val salesData = salesDataList[position]
        holder.bind(salesData)
    }

    override fun getItemCount(): Int {
        return salesDataList.size
    }
}

