package com.example.test2

import Product
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.SelectedorderItemBinding

class SelectedOrderAdapter (val productOrderLists: ArrayList<Product>) : RecyclerView.Adapter<SelectedOrderAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: SelectedorderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.productName
            binding.productQuantity.text = product.productQuantity
            binding.productPrice.textScaleX = product.productPrice?.toFloat() ?: 0.0f
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SelectedorderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productOrderLists[position])
    }

    override fun getItemCount(): Int {
        return productOrderLists.size
    }
}