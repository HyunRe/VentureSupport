package com.example.test2

import Product
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.HomedetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeDetailActivity : AppCompatActivity() {
    private val binding: HomedetailBinding by lazy {
        HomedetailBinding.inflate(layoutInflater)
    }
    private lateinit var homeDetailAdapter: HomeDetailAdapter
    private var productOrderLists = ArrayList<Product>()
    private var intentOrder: Order? = null
    private var intentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        intentOrder = intent.getParcelableExtra("order")
        @Suppress("DEPRECATION")
        intentUser = intent.getParcelableExtra("intentUser")

        binding.date.text = intentOrder?.date.toString()
        binding.SupplierName.text = intentOrder?.supplier?.supplierName
        binding.SupplierPhoneNumber.text = intentOrder?.supplier?.supplierPhoneNumber
        binding.SupplierLocation.text = intentOrder?.supplier?.supplierLocation
        binding.Salary.text = intentOrder?.salary.toString()
        binding.TotalAmount.text = intentOrder?.totalAmount.toString()

        productOrderLists.add(intentOrder?.product!!)

        homeDetailAdapter = HomeDetailAdapter(productOrderLists)
        binding.productRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productRecyclerView.adapter = homeDetailAdapter
        homeDetailAdapter.notifyDataSetChanged()

        binding.acceptButton.setOnClickListener {
            if (intentOrder?.user == null) {
                intentOrder?.user = intentUser
                updateOrder(intentOrder!!)
            }
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        binding.cancelButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun updateOrder(order: Order) {
        val call = RetrofitService.orderService.updateOrder(intentOrder?.orderId!!, order)
        call.enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val updatedOrder = response.body()
                    if (updatedOrder != null) {
                        // Handle the updated order object
                        Log.d("OrderActivity", "Order updated successfully: $updatedOrder")
                    } else {
                        Log.e("OrderActivity", "Failed to update order: No response body")
                    }
                } else {
                    Log.e("OrderActivity", "Failed to update order: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Log.e("OrderActivity", "Network request failed", t)
            }
        })
    }

}