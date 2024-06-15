package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.ExpensechartItemBinding
import com.github.mikephil.charting.data.PieEntry

class ExpenseChartAdapter(private val dataList: List<PieEntry>) : RecyclerView.Adapter<ExpenseChartAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ExpensechartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pieEntry: PieEntry) {
            binding.percentText.text = pieEntry.value.toString()
            binding.breakdownText.text = pieEntry.label
            binding.progressBar.progress = pieEntry.value.toInt()
            binding.expenseText.text = pieEntry.value.toString()

            val progressBarDrawable = when {
                pieEntry.value >= 80 -> R.drawable.red_progress
                pieEntry.value >= 60 -> R.drawable.orange_progress
                pieEntry.value >= 40 -> R.drawable.yellow_progress
                pieEntry.value >= 20 -> R.drawable.green_progress
                else -> R.drawable.blue_progress
            }
            binding.progressBar.progressDrawable = binding.root.context.getDrawable(progressBarDrawable)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ExpensechartItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
