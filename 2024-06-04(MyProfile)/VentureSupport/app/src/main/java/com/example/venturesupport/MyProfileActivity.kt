package com.example.venturesupport

import User
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

    // 사용자 프로필 어댑터
    private lateinit var myprofileAdapter: MyProfileAdapter

    // 차트 어댑터
    private lateinit var mychartAdapter: MyChartAdapter

    // 사용자 프로필 목록
    private var myprofileLists = ArrayList<User>()

    // 차트 목록
    private var chartLists = ArrayList<MyChart>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root

        // 사용자 정보 조회
        getUserById(1)

        // 사용자 프로필 어댑터 초기화 및 클릭 리스너 설정
        myprofileAdapter = MyProfileAdapter(myprofileLists)
        myprofileAdapter.setOnItemClickListener(object : MyProfileAdapter.OnItemClickListeners {
            override fun onItemClick(binding: MyprofileItemBinding, user: User, position: Int) {
                // 사용자 프로필 수정 화면으로 이동
                val intent = Intent(requireContext(), EditProfileActivity::class.java).apply {
                    putExtra("user", user)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        // 리사이클러뷰 설정
        binding.MyProfileRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.MyProfileRecyclerView.adapter = myprofileAdapter

        // 결제 수단 조회 버튼 클릭 시
        binding.PaymentButton.setOnClickListener {
            // PaymentActivity로 이동
            val intent = Intent(requireContext(), PaymentActivity::class.java).apply {
                putExtra("user", myprofileLists.firstOrNull())
            }
            startActivity(intent)
        }

        // 창고 관리 버튼 클릭 시
        binding.WarehouseButton.setOnClickListener {
            if (myprofileLists.isNotEmpty()) {
                // 사용자 정보가 있을 경우 WarehouseActivity로 이동
                val intentUser = myprofileLists[0]
                val intent = Intent(requireContext(), WarehouseActivity::class.java).apply {
                    putExtra("user", intentUser)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "사용자 목록이 비어 있습니다", Toast.LENGTH_SHORT).show()
            }
        }

        // 차트 데이터 설정
        chartLists = ArrayList<MyChart>().apply {
            add(MyChart(R.drawable.sales, "내 매출 내역"))
            add(MyChart(R.drawable.income, "내 수익 내역"))
            add(MyChart(R.drawable.expense, "내 지출 내역"))
        }

        // 차트 어댑터 초기화 및 클릭 리스너 설정
        mychartAdapter = MyChartAdapter(chartLists)
        mychartAdapter.setOnItemClickListener(object : MyChartAdapter.OnItemClickListeners {
            override fun onItemClick(
                binding: MychartItemBinding,
                myChart: MyChart,
                position: Int
            ) {
                val intentUser = myprofileLists.firstOrNull()
                val intent: Intent = when (position) {
                    0 -> Intent(requireContext(), SalesChartActivity::class.java)
                    1 -> Intent(requireContext(), IncomeChartActivity::class.java)
                    2 -> Intent(requireContext(), ExpenseChartActivity::class.java)
                    else -> return
                }.apply {
                    putExtra("user", intentUser)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        // 차트 리사이클러뷰 설정
        binding.ChartRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.ChartRecyclerView.adapter = mychartAdapter

        return view
    }

    // 사용자 정보 조회
    private fun getUserById(userId: Int) {
        val call = RetrofitService.userService.getUserById(userId)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        // 사용자 목록에 추가하고 어댑터 갱신
                        myprofileLists.add(user)
                        myprofileAdapter.updateData(myprofileLists)
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
