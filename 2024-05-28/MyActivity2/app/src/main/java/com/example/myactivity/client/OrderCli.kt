package com.example.myactivity.client

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.myactivity.data.RetrofitClient
import com.example.myactivity.data.model.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderCli(private val context: Context) {

    private val apiService = RetrofitClient.orderService

    // 모든 주문 조회
    fun getAllOrders() {
        apiService.getAllOrders().enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    Log.d("OrderClient", "모든 주문: $orders")
                    Toast.makeText(context, "모든 주문을 불러왔습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("OrderClient", "응답 실패")
                    Toast.makeText(context, "주문 목록을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("OrderClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 주문 ID로 특정 주문 조회
    fun getOrderById(id: Int) {
        apiService.getOrderById(id).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val order = response.body()
                    Log.d("OrderClient", "조회된 주문: $order")
                } else {
                    Log.e("OrderClient", "응답 실패")
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Log.e("OrderClient", "네트워크 오류", t)
            }
        })
    }

    // 새로운 주문 생성
    fun createOrder(order: Order) {
        apiService.createOrder(order).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val createdOrder = response.body()
                    Log.d("OrderClient", "생성된 주문: $createdOrder")
                    Toast.makeText(context, "주문이 생성되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("OrderClient", "응답 실패")
                    Toast.makeText(context, "주문 생성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Log.e("OrderClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 기존 주문 업데이트
    fun updateOrder(id: Int, order: Order) {
        apiService.updateOrder(id, order).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val updatedOrder = response.body()
                    Log.d("OrderClient", "업데이트된 주문: $updatedOrder")
                    Toast.makeText(context, "주문이 업데이트되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("OrderClient", "응답 실패")
                    Toast.makeText(context, "주문 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Log.e("OrderClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 주문 삭제
    fun deleteOrder(id: Int) {
        apiService.deleteOrder(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("OrderClient", "주문이 삭제되었습니다.")
                    Toast.makeText(context, "주문이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("OrderClient", "응답 실패")
                    Toast.makeText(context, "주문 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("OrderClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
