package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.WarehouseItemBinding

class WarehouseAdapter(private var warehouseLists: ArrayList<Warehouse>) : RecyclerView.Adapter<WarehouseAdapter.ViewHolder>() {
    interface OnItemClickListeners {
        fun onItemLongClick(binding: WarehouseItemBinding, warehouse: Warehouse, position: Int)
    }

    private var onItemClickListeners: OnItemClickListeners? = null

    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    fun updateData(newList: ArrayList<Warehouse>) {
        warehouseLists = newList
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        warehouseLists.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, warehouseLists.size)
    }

    inner class ViewHolder(private val binding: WarehouseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(warehouse: Warehouse) {
            binding.warehouseName.text = warehouse.warehouseName
            binding.warehouseLocation.text = warehouse.warehouseLocation

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.root.setOnLongClickListener {
                    onItemClickListeners?.onItemLongClick(binding, warehouse, position)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WarehouseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(warehouseLists[position])
    }

    override fun getItemCount(): Int {
        return warehouseLists.size
    }
}
