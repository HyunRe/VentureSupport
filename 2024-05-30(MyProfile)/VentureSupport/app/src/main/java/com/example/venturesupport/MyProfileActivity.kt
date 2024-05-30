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

class MyProfileActivity: Fragment() {
    private val binding: MyprofileBinding by lazy {
        MyprofileBinding.inflate(layoutInflater)
    }
    private lateinit var myprofileAdapter: MyProfileAdapter
    private lateinit var mychartAdapter: MyChartAdapter
    private var myprofileLists = ArrayList<User>()
    private var chartLists = ArrayList<MyChart>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root

        // 현재 로그인 한 user 정보 가져 오기
        fetchUserById(1)

        // 유저 조회
        myprofileAdapter = MyProfileAdapter(myprofileLists)
        myprofileAdapter.setOnItemClickListener(object : MyProfileAdapter.OnItemClickListeners {
            override fun onItemClick(binding: MyprofileItemBinding, user: User, position: Int) {
                val intentUser = User(
                    userId = user.userId,
                    username = user.username,
                    email = user.email,
                    lat = user.lat,
                    lng = user.lng,
                    phone = user.phone,
                    role = user.role,
                    password = user.password
                )

                val intent = Intent(requireContext(), EditProfileActivity::class.java).apply {
                    putExtra("user", intentUser)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        binding.MyProfileRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.MyProfileRecyclerView.adapter = myprofileAdapter
        myprofileAdapter.notifyDataSetChanged()

        // 결제 수단 조회 (수정 필요)
        binding.PaymentButton.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
            // 결제 수단 창 이동
            // 결제 수단 창 새로 구현 (카드 입력 받는 걸로? 카드사, 카드 이름, 카드 번호 / payment 카드 이름 db에 저장)
            // 지출 내역 입력 창 (지출 내역, 지출 액, 지출 일 - 리사이클러 뷰)
        }

        binding.WarehouseButton.setOnClickListener {
            val intentUser = User(
                userId = myprofileLists[0].userId,
                username = myprofileLists[0].username,
                email = myprofileLists[0].email,
                lat = myprofileLists[0].lat,
                lng = myprofileLists[0].lng,
                phone = myprofileLists[0].phone,
                role = myprofileLists[0].role,
                password = myprofileLists[0].password
            )

            val intent = Intent(requireContext(), WarehouseActivity::class.java).apply {
                putExtra("user", intentUser)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        chartLists = ArrayList<MyChart>().apply {
            add(MyChart(R.drawable.sales, "내 매출 내역"))
            add(MyChart(R.drawable.income, "내 수익 내역"))
            add(MyChart(R.drawable.expense, "내 지출 내역"))
        }

        mychartAdapter = MyChartAdapter(chartLists)
        mychartAdapter.setOnItemClickListener(object : MyChartAdapter.OnItemClickListeners {
            override fun onItemClick(binding: MychartItemBinding, myChart: MyChart, position: Int) {
                val intentUser = User(
                    userId = myprofileLists[0].userId,
                    username = myprofileLists[0].username,
                    email = myprofileLists[0].email,
                    lat = myprofileLists[0].lat,
                    lng = myprofileLists[0].lng,
                    phone = myprofileLists[0].phone,
                    role = myprofileLists[0].role,
                    password = myprofileLists[0].password
                )

                val intent: Intent = when (position) {
                    0 -> {
                        Intent(requireContext(), SalesChartActivity::class.java).apply {
                            putExtra("user", intentUser)
                        }
                    }
                    1 -> {
                        Intent(requireContext(), IncomeChartActivity::class.java).apply {
                            putExtra("user", intentUser)
                        }
                    }
                    2 -> {
                        Intent(requireContext(), ExpenseChartActivity::class.java).apply {
                            putExtra("user", intentUser)
                        }
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

    /*private fun loadPaymentById(userId: Int) {
        val call = RetrofitService.paymentService.getPaymentById(userId)
        call.enqueue(object : Callback<Payment> {
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if (response.isSuccessful) {
                    val payment = response.body()
                    return payment.paymentName
                } else {
                    Toast.makeText(this@PaymentActivity, "결제 수단을 추가해 주세요", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Payment>, t: Throwable) {
                Toast.makeText(this@PaymentActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }*/

    // 현재 로그인 한 user 정보 가져 오기
    private fun fetchUserById(userId: Int) {
        val call = RetrofitService.userService.getUserById(userId)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        myprofileLists.add(user)
                        myprofileAdapter.updateData(myprofileLists)
                        Log.d("MainActivity", "User added to list: $user")
                    } else {
                        Log.e("MainActivity", "No user data found in response")
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