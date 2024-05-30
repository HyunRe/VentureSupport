package com.example.test2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

class SelectedOrderActivity: AppCompatActivity() {

    class SelectedOrderActivity : AppCompatActivity() {
        private lateinit var binding: ActivitySelectedOrderBinding
        private lateinit var selectedOrderAdapter: SelectedOrderAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivitySelectedOrderBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val order = intent.getParcelableExtra<Order>("order")

            binding.dateTextView.text = order?.date.toString()
            binding.userEmail.text = order?.user?.userEmail
            binding.supplierName.text = order?.supplier?.supplierName
            binding.supplierPhoneNumber.text = order?.supplier?.supplierPhoneNumber
            binding.supplierLocation.text = order?.supplier?.supplierLocation
            binding.salary.text = "${order?.salary} 원"
            binding.totalAmount.text = "${order?.totalAmount} 원"

            setupRecyclerView(order?.products ?: emptyList())
        }

        private fun setupRecyclerView(products: List<Product>) {
            selectedOrderAdapter = SelectedOrderAdapter(products)
            binding.productRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.productRecyclerView.adapter = selectedOrderAdapter
        }
    }

}