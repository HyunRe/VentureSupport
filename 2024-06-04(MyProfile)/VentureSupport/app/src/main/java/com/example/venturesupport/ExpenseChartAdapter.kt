package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.ExpensechartItemBinding
import com.github.mikephil.charting.data.PieEntry

class ExpenseChartAdapter(private val dataList: List<PieEntry>) : RecyclerView.Adapter<ExpenseChartAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ExpensechartItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pieEntry: PieEntry) {
            binding.percentText.text = pieEntry.value.toString() // 퍼센트 텍스트 설정
            binding.breakdownText.text = pieEntry.label // 라벨 텍스트 설정
            binding.progressBar.progress = pieEntry.value.toInt() // 프로그레스바 진행도 설정

            // 프로그레스바의 색상을 pieEntry 값에 따라 설정
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
