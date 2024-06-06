package com.example.test2

import Product
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.CreateorderItemBinding

class CreateOrderAdapter(private var productLists: ArrayList<Product>) : RecyclerView.Adapter<CreateOrderAdapter.ViewHolder>() {
    interface OnItemClickListeners {
        fun onItemLongClick(binding: CreateorderItemBinding, product: Product, position: Int)
    }

    private var onItemClickListeners: OnItemClickListeners? = null

    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    fun updateData(newList: ArrayList<Product>) {
        productLists = newList
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        productLists.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, productLists.size)
    }

    inner class ViewHolder(private val binding: CreateorderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.productName
            binding.productQuantity.text = product.productQuantity
            binding.productPrice.textScaleX = product.productPrice?.toFloat() ?: 0.0f

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.root.setOnLongClickListener {
                    onItemClickListeners?.onItemLongClick(binding, product, position)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CreateorderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productLists[position])
    }

    override fun getItemCount(): Int {
        return productLists.size
    }
}