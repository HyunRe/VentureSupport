package com.example.test2

import Product
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.CreateorderItemBinding
import com.example.test2.databinding.SelectedorderBinding

class SelectedOrderActivity: AppCompatActivity() {
    private val binding: SelectedorderBinding by lazy {
        SelectedorderBinding.inflate(layoutInflater)
    }
    private lateinit var selectedOrderAdapter: SelectedOrderAdapter
    private var productOrderLists = ArrayList<Product>()
    private var selectedOrder: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        selectedOrder = intent.getParcelableExtra("selectedOrder")

        binding.date.text = selectedOrder?.date.toString()
        binding.SupplierName.text = selectedOrder?.supplier?.supplierName
        binding.SupplierPhoneNumber.text = selectedOrder?.supplier?.supplierPhoneNumber
        binding.SupplierLocation.text = selectedOrder?.supplier?.supplierLocation
        binding.Salary.text = selectedOrder?.salary.toString()
        binding.TotalAmount.text = selectedOrder?.totalAmount.toString()

        productOrderLists.add(selectedOrder?.product!!)

        selectedOrderAdapter = SelectedOrderAdapter(productOrderLists)
        binding.productRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productRecyclerView.adapter = selectedOrderAdapter
        selectedOrderAdapter.notifyDataSetChanged()
    }
}