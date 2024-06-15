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

class MyProfileActivity : Fragment() {

    private val binding: MyprofileBinding by lazy {
        MyprofileBinding.inflate(layoutInflater)
    }
    private lateinit var myprofileAdapter: MyProfileAdapter
    private lateinit var mychartAdapter: MyChartAdapter
    private var myprofileLists = ArrayList<User>()
    private var chartLists = ArrayList<MyChart>()
    private var intentUser: User? = null

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root

        @Suppress("DEPRECATION")
        intentUser = arguments?.getParcelable<User>("intentUser")

        // 수정 필요
        getUserById(1)
        //getUserById(intentUser?.userId!!)

        myprofileAdapter = MyProfileAdapter(myprofileLists)
        myprofileAdapter.setOnItemClickListener(object : MyProfileAdapter.OnItemClickListeners {
            override fun onItemClick(binding: MyprofileItemBinding, user: User, position: Int) {
                val intent = Intent(requireContext(), EditProfileActivity::class.java).apply {
                    putExtra("intentUser", user)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        binding.MyProfileRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.MyProfileRecyclerView.adapter = myprofileAdapter

        binding.PaymentButton.setOnClickListener {
            val intent = Intent(requireContext(), PaymentActivity::class.java).apply {
                // 수정 필요
                putExtra("intentUser", myprofileLists.firstOrNull())
                //putExtra("intentUser", intentUser)
            }
            startActivity(intent)
        }

        binding.WarehouseButton.setOnClickListener {
            if (myprofileLists.isNotEmpty()) {
                val intent = Intent(requireContext(), WarehouseActivity::class.java).apply {
                    putExtra("intentUser", myprofileLists[0])
                    //putExtra("intentUser", intentUser)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "사용자 목록이 비어 있습니다", Toast.LENGTH_SHORT).show()
            }
        }

        chartLists = ArrayList<MyChart>().apply {
            add(MyChart(R.drawable.sales, "내 매출 내역"))
            add(MyChart(R.drawable.income, "내 수익 내역"))
            add(MyChart(R.drawable.expense, "내 지출 내역"))
        }

        mychartAdapter = MyChartAdapter(chartLists)
        mychartAdapter.setOnItemClickListener(object : MyChartAdapter.OnItemClickListeners {
            override fun onItemClick(
                binding: MychartItemBinding,
                myChart: MyChart,
                position: Int
            ) {
                // 수정 필요
                val intentUser = myprofileLists.firstOrNull()
                val intent: Intent = when (position) {
                    0 -> Intent(requireContext(), SalesChartActivity::class.java)
                    1 -> Intent(requireContext(), IncomeChartActivity::class.java)
                    2 -> Intent(requireContext(), ExpenseChartActivity::class.java)
                    else -> return
                }.apply {
                    // 수정 필요
                    putExtra("intentUser", intentUser)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        binding.ChartRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.ChartRecyclerView.adapter = mychartAdapter

        return view
    }

    private fun getUserById(userId: Int) {
        val call = RetrofitService.userService.getUserById(userId)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        myprofileLists.add(user)
                        myprofileAdapter.notifyDataSetChanged()
                        Log.d("MyProfileActivity", "User added to list: $user")
                    } else {
                        Log.e("MyProfileActivity", "No user data found in response")
                    }
                } else {
                    Log.e("MyProfileActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("MyProfileActivity", "Network request failed", t)
            }
        })
    }
}
