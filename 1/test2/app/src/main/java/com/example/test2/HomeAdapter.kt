package com.example.test2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.HomeItemBinding

class HomeAdapter(private var orderLists: List<Order>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    interface OnItemClickListeners {
        fun onItemClick(binding: HomeItemBinding, order: Order, position: Int)
    }

    private var onItemClickListeners: OnItemClickListeners? = null

    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    fun updateData(newList: List<Order>) {
        orderLists = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: HomeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.date.text = order.date.toString()
            binding.name.text = order.supplier.supplierName
            binding.location.text = order.supplier.supplierLocation
            binding.salary.text = order.salary.toString()

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.root.setOnClickListener {
                    onItemClickListeners?.onItemClick(binding, order, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderLists[position])
    }

    override fun getItemCount(): Int {
        return orderLists.size
    }
}