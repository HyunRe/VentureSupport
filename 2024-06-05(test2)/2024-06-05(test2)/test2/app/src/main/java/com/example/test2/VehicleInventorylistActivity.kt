package com.example.test2

import Product
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.VehicleinventorylistBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleInventorylistActivity: AppCompatActivity() {
    private val binding: VehicleinventorylistBinding by lazy {
        VehicleinventorylistBinding.inflate(layoutInflater)
    }
    private lateinit var vehicleInventorylistAdapter: VehicleInventorylistAdapter
    private var productLists = ArrayList<Product>()
    private var intentOrder: Order? = null
    private var intentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        intentUser = intent.getParcelableExtra("intentUser")
        @Suppress("DEPRECATION")
        intentOrder = intent.getParcelableExtra("intentOrder")

        getOrdersByUserId(intentUser?.userId!!)

        // 롱클릭으로 삭제 추가
        vehicleInventorylistAdapter = VehicleInventorylistAdapter(productLists)
        binding.inventoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.inventoryRecyclerView.adapter = vehicleInventorylistAdapter
        vehicleInventorylistAdapter.notifyDataSetChanged()

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddVehicleInventoryActivity::class.java)
            intent.putExtra("intentUser", intentUser)
            intent.putExtra("intentOrder", intentOrder)
            startActivity(intent)
        }
    }

    // vehicle Inventroy로 변경해야함
    private fun getOrdersByUserId(userId: Int) {
        val call = RetrofitService.orderService.getOrdersByUserId(userId)
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    if (orders != null) {
                        for (order in orders) {
                            val product = order.product
                            if (product != null) {
                                productLists.addAll(listOf(product))
                                Log.d("IncomeChartActivity", "Products added to list: $product")
                            } else {
                                Log.e("IncomeChartActivity", "No products found in order: $order")
                            }
                        }
                    } else {
                        Log.e("IncomeChartActivity", "No order data found in response")
                    }
                } else {
                    Log.e("IncomeChartActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("IncomeChartActivity", "Network request failed", t)
            }
        })
    }

}

