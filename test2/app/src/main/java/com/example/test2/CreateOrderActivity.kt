package com.example.test2

import OrderApi
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
    //private var productLists = ArrayList<Product>()
    //private var user: User? = null

    private var user: User? = null
    private val orderApi: OrderApi = RetrofitService.orderService.createOrder(order = Order())

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
            createOrder(order)
        }
    }
    private fun createOrder(order: Order) {
        orderApi.createOrder(order).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CreateOrderActivity, "Order created successfully", Toast.LENGTH_SHORT).show()
                    // 성공적으로 오더가 생성되면 이전 액티비티로 돌아감
                    finish()
                } else {
                    // 오더 생성 실패 시 데모 데이터를 보여줌
                    showDemoData()
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                // 통신 실패 시 데모 데이터를 보여줌
                showDemoData()
            }
        })
    }

    private fun showDemoData() {
        // 데모 데이터 설정
        binding.date.setText("2024-05-09")
        binding.SupplierName.setText("한성 마켓")
        binding.SupplierPhoneNumber.setText("010-1111-1111")
        binding.SupplierLocation.setText("서울특별시 성북구 신사동")
        binding.Salary.setText("32000")
        binding.TotalAmount.setText("240000")
    }
}