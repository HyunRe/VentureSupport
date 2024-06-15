package com.example.test2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.VehicleinventorylistItemBinding

class VehicleInventorylistAdapter (val inverntorytLists: ArrayList<VehicleInventory>) : RecyclerView.Adapter<VehicleInventorylistAdapter.ViewHolder>() {
    interface OnItemClickListeners {
        fun onItemLongClick(binding: VehicleinventorylistItemBinding, vehicleInventory: VehicleInventory, position: Int)
    }

    private var onItemClickListeners: OnItemClickListeners? = null

    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    fun removeItem(position: Int) {
        inverntorytLists.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, inverntorytLists.size)
    }

    inner class ViewHolder(private val binding: VehicleinventorylistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vehicleInventory: VehicleInventory) {
            binding.productName.text = vehicleInventory.productName
            binding.productQuantity.text = vehicleInventory.vehicleInventoryQuantity

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.root.setOnLongClickListener {
                    onItemClickListeners?.onItemLongClick(binding, vehicleInventory, position)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VehicleinventorylistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(inverntorytLists[position])
    }

    override fun getItemCount(): Int {
        return inverntorytLists.size
    }
}