package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.MychartItemBinding

class MyChartAdapter(private val chartLists: List<MyChart>) : RecyclerView.Adapter<MyChartAdapter.ProfileViewHolder>() {
    interface OnItemClickListeners {
        fun onItemClick(binding: MychartItemBinding, myChart: MyChart, position: Int)
    }

    private var onItemClickListeners: OnItemClickListeners? = null

    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    inner class ProfileViewHolder(private val binding: MychartItemBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = MychartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(chartLists[position])
    }

    override fun getItemCount(): Int {
        return chartLists.size
    }
}
