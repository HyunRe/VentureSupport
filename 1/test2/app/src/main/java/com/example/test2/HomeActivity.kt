package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.HomeBinding
import com.example.test2.databinding.HomeItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

class HomeActivity : Fragment() {
    private val binding: HomeBinding by lazy {
        HomeBinding.inflate(layoutInflater)
    }
    private lateinit var homeAdapter: HomeAdapter
    private val orderLists = ArrayList<Order>()
    private var intentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root

        @Suppress("DEPRECATION")
        arguments?.let {
            intentUser = it.getParcelable("intentUser")
        }

        binding.count.text = orderLists.size.toString()

        fetchAllOrders()

        homeAdapter = HomeAdapter(orderLists)
        homeAdapter.setOnItemClickListener(object : HomeAdapter.OnItemClickListeners {
            override fun onItemClick(binding: HomeItemBinding, order: Order, position: Int) {
                val intent = Intent(requireContext(), HomeDetailActivity::class.java)
                intent.putExtra("intentUser", intentUser)
                intent.putExtra("order", order)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        binding.orderlistRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.orderlistRecyclerView.adapter = homeAdapter
        homeAdapter.notifyDataSetChanged()


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.selectedorderlist -> {
                val intent = Intent(requireContext(), HomeDetailActivity::class.java)
                intent.putExtra("intentUser", intentUser)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                Toast.makeText(requireContext(), "수락 물류 운송 건 목록으로 이동", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.vehicleinventorylist -> {
                val intent = Intent(requireContext(), HomeDetailActivity::class.java)
                intent.putExtra("intentUser", intentUser)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                Toast.makeText(requireContext(), "차량 재고 관리로 이동", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // 유저가 null인 것만 보이게 설정
    private fun fetchAllOrders() {
        val call = RetrofitService.orderService.getAllOrders()
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    if (orders != null) {
                        val currentDate = DateUtils.getCurrentDate()
                        val filteredOrders = orders.filter { order ->
                            // assuming order.date is in the format "yyyy-MM-dd"
                            order.date == currentDate && order.user != null
                        }
                        orderLists.addAll(filteredOrders)
                        homeAdapter.notifyDataSetChanged()
                        Log.d("OrderListActivity", "Orders fetched successfully")
                    } else {
                        Log.e("OrderListActivity", "No orders found in response")
                    }
                } else {
                    Log.e("OrderListActivity", "Failed to fetch orders: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("OrderListActivity", "Network request failed", t)
            }
        })
    }

    object DateUtils {
        fun getCurrentDate(): Date {
            return Date()
        }
    }
}
