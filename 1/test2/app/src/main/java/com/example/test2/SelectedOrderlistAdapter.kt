package com.example.test2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.SelectedorderlistItemBinding

class SelectedOrderlistAdapter(val selectedOrderLists: ArrayList<Order>) : RecyclerView.Adapter<SelectedOrderlistAdapter.ViewHolder>() {
    private var itemClickListeners: OnItemClickListeners? = null

    interface OnItemClickListeners {
        fun onItemClick(binding: SelectedorderlistItemBinding, order: Order, position: Int)
    }

    fun setOnItemClickListener(itemClickListeners: OnItemClickListeners) {
        this.itemClickListeners = itemClickListeners
    }

    inner class ViewHolder(private val binding: SelectedorderlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListeners?.onItemClick(binding, selectedOrderLists[position], position)
                }
            }
        }

        fun bind(order: Order) {
            binding.date.text = order.date.toString()
            binding.name.text = order.supplier.supplierName
            binding.location.text = order.supplier.supplierLocation
            binding.salary.text = order.salary.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SelectedorderlistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(selectedOrderLists[position])
    }

    override fun getItemCount(): Int {
        return selectedOrderLists.size
    }
}