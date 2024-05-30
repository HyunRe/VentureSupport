package com.example.test2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.SelectedorderItemBinding


class SelectedOrderAdapter(private val productList: List<Product>) {
    data class Product(
        val productName: String,
        val productDescription: String,
        val quantity: Int,
        val price: Double
    )

    class SelectedOrderAdapter(private val products: List<Product>) : RecyclerView.Adapter<SelectedOrderAdapter.ViewHolder>() {

        inner class ViewHolder(private val binding: SelectedorderItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(product: Product) {
                binding.productName.text = product.productName
                binding.productDescription.text = product.productDescription
                binding.productQuantity.text = "${product.quantity} box"
                binding.productPrice.text = "${product.price} 원"
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = SelectedorderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(products[position])
        }

        override fun getItemCount(): Int = products.size
    }


}