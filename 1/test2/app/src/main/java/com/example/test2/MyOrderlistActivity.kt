package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.MyorderlistBinding
import com.example.test2.databinding.MyorderlistItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOrderlistActivity: AppCompatActivity() {
    private val binding: MyorderlistBinding by lazy {
        MyorderlistBinding.inflate(layoutInflater)
    }
    private lateinit var myOrderlistAadpter: MyOrderlistAadpter
    private var myOrderLists = ArrayList<Order>()
    private var company: Company? = null
    private var intentOrder: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        company = intent.getParcelableExtra("intentCompany")
        @Suppress("DEPRECATION")
        intentOrder = intent.getParcelableExtra("intentOrder")

        val id = company?.companyId ?: intentOrder?.company?.companyId ?: 0

        getOrdersByCompanyId(id)

        binding.count.text = myOrderLists.size.toString()

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            intent.putExtra("intentCompany", company)
            startActivity(intent)
        }

        myOrderlistAadpter = MyOrderlistAadpter(myOrderLists)
        myOrderlistAadpter.setOnItemClickListener(object : MyOrderlistAadpter.OnItemClickListeners {
            override fun onItemClick(binding: MyorderlistItemBinding, order: Order, position: Int) {
                val intent = Intent(this@MyOrderlistActivity, EditOrderActivity::class.java).apply {
                    putExtra("editOrder", myOrderLists[position])
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            override fun onItemLongClick(binding: MyorderlistItemBinding, order: Order, position: Int) {
                val builder = AlertDialog.Builder(this@MyOrderlistActivity)
                builder.setTitle("삭제 확인")
                builder.setMessage("이 아이템을 삭제하시겠습니까?")

                builder.setPositiveButton("삭제") { dialog, which ->
                    myOrderlistAadpter.removeItem(position)
                    val orderId = order.orderId?: error("Order ID가 null입니다.")
                    deleteOrder(orderId)
                }

                builder.setNegativeButton("취소") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        })

        binding.myorderlistRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.myorderlistRecyclerView.adapter = myOrderlistAadpter
        myOrderlistAadpter.notifyDataSetChanged()
    }

    private fun getOrdersByCompanyId(companyId: Int) {
        val call = RetrofitService.orderService.getOrdersByCompanyId(companyId)
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val order = response.body()
                    if (order != null) {
                        myOrderLists.addAll(order)
                        Log.d("IncomeChartActivity", "Order added to list: $order")
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

    private fun deleteOrder(orderId: Int) {
        val call = RetrofitService.orderService.deleteOrder(orderId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("CreateOrderActivity", "서버에서 아이템 삭제 성공")
                } else {
                    Log.e("CreateOrderActivity", "서버에서 아이템 삭제 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("CreateOrderActivity", "서버와의 통신 실패", t)
            }
        })
    }
}