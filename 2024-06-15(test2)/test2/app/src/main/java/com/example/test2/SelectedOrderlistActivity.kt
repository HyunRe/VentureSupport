package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.SelectedorderlistBinding
import com.example.test2.databinding.SelectedorderlistItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectedOrderlistActivity: AppCompatActivity() {
    private val binding: SelectedorderlistBinding by lazy {
        SelectedorderlistBinding.inflate(layoutInflater)
    }
    private lateinit var selectedOrderlistAdapter: SelectedOrderlistAdapter
    private var selectedOrderLists = ArrayList<Order>()
    private var intentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        intentUser = intent.getParcelableExtra("intentUser")

        getOrdersByUserId(intentUser?.userId!!)

        selectedOrderlistAdapter = SelectedOrderlistAdapter(selectedOrderLists)
        selectedOrderlistAdapter.setOnItemClickListener(object : SelectedOrderlistAdapter.OnItemClickListeners {
            override fun onItemClick(binding: SelectedorderlistItemBinding, order: Order, position: Int) {
                val intent = Intent(this@SelectedOrderlistActivity, SelectedOrderActivity::class.java).apply {
                    putExtra("selectedOrder", order)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        binding.myorderlistRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.myorderlistRecyclerView.adapter = selectedOrderlistAdapter

        binding.exitButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("intentUser", intentUser)
            startActivity(intent)
            finish()
        }
    }

    private fun getOrdersByUserId(userId: Int) {
        val call = RetrofitService.orderService.getOrdersByUserId(userId)
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val order = response.body()
                    if (order != null) {
                        val currentDate = HomeActivity.DateUtils.getCurrentDate()
                        Log.d("HomeActivity", "Current Date: $currentDate")
                        val filteredOrders = order.filter { order ->
                            // assuming order.date is in the format "yyyy-MM-dd"
                            order.date == currentDate && order.user == null
                        }
                        Log.d("HomeActivity", "Filtered Orders: $filteredOrders")

                        selectedOrderLists.addAll(order)
                        Log.d("IncomeChartActivity", "Order added to list: $order")
                        binding.count.text = selectedOrderLists.size.toString()
                        selectedOrderlistAdapter.notifyDataSetChanged()
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