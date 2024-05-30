package com.example.test2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class HomeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val order = intent.getParcelableExtra<Order>("order")

        order?.let {
            binding.apply {
                orderDate.text = it.date.toString()
                orderProductName.text = it.product.productName
                orderCompanyName.text = it.company.companyName
                orderUserEmail.text = it.user.userEmail
                orderSupplierName.text = it.supplier.supplierName
                orderSupplierLocation.text = it.supplier.supplierLocation
                orderSupplierPhoneNumber.text = it.supplier.supplierPhoneNumber
                orderSalary.text = it.salary.toString()
                orderTotalAmount.text = it.totalAmount.toString()
            }
        }
    }
}