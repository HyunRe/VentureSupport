package com.example.test2

import Product
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.HomedetailItemBinding
import com.example.test2.databinding.VehicleinventorylistItemBinding

class VehicleInventorylistAdapter (val productLists: ArrayList<Product>) : RecyclerView.Adapter<VehicleInventorylistAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: VehicleinventorylistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.productName
            binding.productQuantity.text = product.productQuantity
            binding.productPrice.textScaleX = product.productPrice?.toFloat() ?: 0.0f
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
}