package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.MychartItemBinding

class MyChartAdapter(private val chartLists: List<MyChart>) : RecyclerView.Adapter<MyChartAdapter.ViewHolder>() {
    interface OnItemClickListeners {
        fun onItemClick(binding: MychartItemBinding, myChart: MyChart, position: Int)
    }

    private var onItemClickListeners: OnItemClickListeners? = null

    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    inner class ViewHolder(private val binding: MychartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myChart: MyChart) {
            binding.mychartImage.setImageResource(myChart.imageResId)
            binding.mychartText.text = myChart.text

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.root.setOnClickListener {
                    onItemClickListeners?.onItemClick(binding, myChart, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MychartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chartLists[position])
    }

    override fun getItemCount(): Int {
        return chartLists.size
    }
}
