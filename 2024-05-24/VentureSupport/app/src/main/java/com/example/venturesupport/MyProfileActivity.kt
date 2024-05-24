package com.example.venturesupport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.venturesupport.databinding.MyprofileBinding
import com.example.venturesupport.databinding.MychartItemBinding
import com.example.venturesupport.databinding.MyprofileItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfileActivity: Fragment() {
    private val binding: MyprofileBinding by lazy {
        MyprofileBinding.inflate(layoutInflater)
    }
    private lateinit var naverPayApiService: NaverPayApiService
    private lateinit var myprofileAdapter: MyProfileAdapter
    private lateinit var mychartAdapter: MyChartAdapter
    private var myprofileLists = ArrayList<User>()
    private var chartLists = ArrayList<MyChart>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root

        // 현재 로그인 한 user 정보 가져 오기 (구현 필요)
        fetchUserById("6")

        myprofileAdapter = MyProfileAdapter(myprofileLists)
        myprofileAdapter.setOnItemClickListener(object : MyProfileAdapter.OnItemClickListeners {
            override fun onItemClick(binding: MyprofileItemBinding, user: User, position: Int) {
                val intent: Intent = when (position) {
                    0 -> {
                        Intent(requireContext(), EditProfileActivity::class.java)
                    }
                    else -> return
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        binding.MyProfileRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.MyProfileRecyclerView.adapter = myprofileAdapter

        // 수정필요
        naverPayApiService = NaverPayApiService()
        binding.NaverPayButton.setOnClickListener {
            val orderId = "order1234"
            val price = 10000
            naverPayApiService.makePayment(orderId, price) { success, message ->
                if (success) {
                    Toast.makeText(requireActivity(), "Payment successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), "Payment failed: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }

        chartLists = ArrayList<MyChart>().apply {
            add(MyChart(R.drawable.sales, "내 매출 내역"))
            add(MyChart(R.drawable.income, "내 수익 내역"))
            add(MyChart(R.drawable.expense, "내 지출 내역"))
        }

        mychartAdapter = MyChartAdapter(chartLists)
        mychartAdapter.setOnItemClickListener(object : MyChartAdapter.OnItemClickListeners {
            override fun onItemClick(binding: MychartItemBinding, myChart: MyChart, position: Int) {
                val intent: Intent = when (position) {
                    0 -> {
                        Intent(requireContext(), SalesChartActivity::class.java)
                    }
                    1 -> {
                        Intent(requireContext(), IncomeChartActivity::class.java)
                    }
                    2 -> {
                        Intent(requireContext(), ExpenseChartActvity::class.java)
                    }
                    else -> return
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        binding.ChartRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.ChartRecyclerView.adapter = mychartAdapter

        return view
    }

    private fun fetchUserById(id: String) {
        val call = RetrofitService.userService.getUserById(id)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        myprofileLists.add(user)
                        Log.d("MainActivity", "User added to list: $user")
                    }
                } else {
                    Log.e("MainActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("MainActivity", "Network request failed", t)
            }
        })
    }

}