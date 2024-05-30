package com.example.test2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.VehicleinventorylistItemBinding

class VehicleInventorylistAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<VehicleInventorylistAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = VehicleinventorylistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = VehicleinventorylistItemBinding.bind(itemView)

        fun bind(product: Product) {
            binding.productName.text = product.productName ?: ""
            binding.productDescription.text = product.productPrice ?.toString()?: ""
            binding.productQuantity.text = product.productQuantity?.toString() ?: ""
        }
    }
}
