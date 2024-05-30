package com.example.test2

import Order
import Product
import User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.CreateorderBinding
import com.example.test2.databinding.CreateorderItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

class CreateOrderActivity: AppCompatActivity() {
    private val binding: CreateorderBinding by lazy {
        CreateorderBinding.inflate(layoutInflater)
    }
    private var productLists = ArrayList<Product>()
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        user = intent.getParcelableExtra("intentUser")

        binding.submitButton.setOnClickListener {
            val date = binding.date.text.toString()
            val SupplierName = binding.SupplierName.text.toString()
            val SupplierPhoneNumber = binding.SupplierPhoneNumber.text.toString()
            val SupplierLocation = binding.SupplierLocation.text.toString()
            val Salary = binding.Salary.text.toString()
            val TotalAmount = binding.TotalAmount.text.toString()

            val order = Order(
                orderId = null,
                date = Date(date.toLong()),
                supplierName = SupplierName,
                supplierPhoneNumber = SupplierPhoneNumber,
                supplierLocation = SupplierLocation,
                salary = Salary.toDouble(),
                totalAmount = TotalAmount.toInt(),
                user = user!!
            )
        }
    }
}