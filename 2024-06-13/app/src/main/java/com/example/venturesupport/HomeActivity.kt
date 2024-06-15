package com.example.venturesupport

import User
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
import com.example.venturesupport.databinding.HomeBinding
import com.example.venturesupport.databinding.HomeItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

/**
 * 홈 화면을 보여주는 Fragment 클래스입니다.
 */
class HomeActivity : Fragment() {
    // 바인딩 변수: 레이아웃과 연결하여 UI 요소에 접근할 수 있도록 합니다.
    private val binding: HomeBinding by lazy {
        HomeBinding.inflate(layoutInflater)
    }
    private lateinit var homeAdapter: HomeAdapter
    private val orderLists = ArrayList<Order>()
    private var intentUser: User? = null
    private var intentOrder: Order? = null

    /**
     * Fragment의 뷰가 생성될 때 호출됩니다.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root

        // 인텐트를 통해 전달된 사용자 정보를 가져옵니다.
        @Suppress("DEPRECATION")
        arguments?.let {
            intentUser = it.getParcelable("intentUser")
        }

        // 초기 주문 목록의 개수를 표시합니다.
        binding.count.text = orderLists.size.toString()

        // 모든 주문을 가져옵니다.
        fetchAllOrders()

        // 어댑터 초기화 및 클릭 리스너 설정
        homeAdapter = HomeAdapter(orderLists)
        homeAdapter.setOnItemClickListener(object : HomeAdapter.OnItemClickListeners {
            override fun onItemClick(binding: HomeItemBinding, order: Order, position: Int) {
                intentOrder = order
                Log.d("onItemClick", "Orders fetched successfully: ${intentOrder}")
                val intent = Intent(requireContext(), HomeDetailActivity::class.java)
                intent.putExtra("intentUser", intentUser)
                intent.putExtra("order", order)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        // RecyclerView 설정
        binding.orderlistRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.orderlistRecyclerView.adapter = homeAdapter
        homeAdapter.notifyDataSetChanged()

        return view
    }

    /**
     * 옵션 메뉴를 생성하는 함수입니다.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * 옵션 메뉴 항목 클릭 시 처리하는 함수입니다.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.selectedorderlist -> {
                // 수락 물류 운송 건 목록으로 이동
                val intent = Intent(requireContext(), HomeDetailActivity::class.java)
                intent.putExtra("intentUser", intentUser)
                intent.putExtra("intentOrder", intentOrder)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                Toast.makeText(requireContext(), "수락 물류 운송 건 목록으로 이동", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.vehicleinventorylist -> {
                // 차량 재고 관리 화면으로 이동
                val intent = Intent(requireContext(), HomeDetailActivity::class.java)
                intent.putExtra("intentUser", intentUser)
                intent.putExtra("intentOrder", intentOrder)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                Toast.makeText(requireContext(), "차량 재고 관리로 이동", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * 모든 주문을 가져오는 함수입니다.
     */
    private fun fetchAllOrders() {
        val call = RetrofitService.orderService.getAllOrders()
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    if (orders != null) {
                        // 현재 날짜와 일치하는 주문만 필터링합니다.
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

    /**
     * 현재 날짜를 반환하는 유틸리티 객체입니다.
     */
    object DateUtils {
        fun getCurrentDate(): Date {
            return Date()
        }
    }
}
