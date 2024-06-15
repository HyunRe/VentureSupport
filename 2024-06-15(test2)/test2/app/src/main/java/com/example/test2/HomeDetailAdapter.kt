package com.example.test2

import Product
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.HomedetailItemBinding

class HomeDetailAdapter (val productOrderLists: ArrayList<Product>) : RecyclerView.Adapter<HomeDetailAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: HomedetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.productName
            binding.productQuantity.text = product.productQuantity
            binding.productPrice.text = product.productPrice.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomedetailItemBinding.inflate(
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