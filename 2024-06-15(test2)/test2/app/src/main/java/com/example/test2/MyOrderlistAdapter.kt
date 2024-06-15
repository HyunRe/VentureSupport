package com.example.test2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.MyorderlistItemBinding

class MyOrderlistAdapter(val myOrderLists: ArrayList<Order>) : RecyclerView.Adapter<MyOrderlistAdapter.ViewHolder>() {
    private var itemClickListeners: OnItemClickListeners? = null

    interface OnItemClickListeners {
        fun onItemClick(binding: MyorderlistItemBinding, order: Order, position: Int)
        fun onItemLongClick(binding: MyorderlistItemBinding, order: Order, position: Int)
    }

    fun setOnItemClickListener(itemClickListeners: OnItemClickListeners) {
        this.itemClickListeners = itemClickListeners
    }

    fun removeItem(position: Int) {
        myOrderLists.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, myOrderLists.size)
    }

    inner class ViewHolder(private val binding: MyorderlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListeners?.onItemClick(binding, myOrderLists[position], position)
                }
            }

            binding.root.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListeners?.onItemLongClick(binding, myOrderLists[position], position)
                }
                true
            }
        }

        fun bind(order: Order) {
            binding.date.text = order.date.toString()
            binding.name.text = order.supplier.supplierName
            binding.location.text = order.supplier.supplierLocation
            binding.salary.text = order.salary.toString()
        }
    }

    // 데이터 추가 후 해당 범위에 대한 알림 메서드
    fun addOrders(newOrders: List<Order>) {
        val previousSize = myOrderLists.size
        myOrderLists.addAll(newOrders)
        notifyItemRangeInserted(previousSize, newOrders.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyorderlistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myOrderLists[position])
    }

    override fun getItemCount(): Int {
        return myOrderLists.size
    }
}
