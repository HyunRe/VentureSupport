package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.VehicleinventorylistItemBinding

class VehicleInventorylistAdapter (
    val productLists: ArrayList<Product>,
    private val onLongClick: (Product) -> Unit
) : RecyclerView.Adapter<VehicleInventorylistAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: VehicleinventorylistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.productName
            binding.productQuantity.text = product.productQuantity

            // 롱클릭 리스너 추가
            binding.root.setOnLongClickListener {
                onLongClick(product)
                true
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
        holder.bind(productLists[position])
    }

    override fun getItemCount(): Int {
        return productLists.size
    }

    // 아이템 삭제 메서드
    fun removeItem(product: Product) {
        val position = productLists.indexOf(product)
        if (position >= 0) {
            productLists.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}