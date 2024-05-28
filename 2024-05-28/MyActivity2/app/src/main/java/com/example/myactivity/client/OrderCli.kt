package com.example.myactivity.client

import android.util.Log
import com.example.myactivity.data.model.Order
import com.example.myactivity.data.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderCli {

    private val apiService = RetrofitClient.apiService

    // 모든 주문 조회
    fun getAllOrders() {
        apiService.getAllOrders().enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    Log.d("OrderClient", "모든 주문: $orders")
                } else {
                    Log.e("OrderClient", "응답 실패")
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("OrderClient", "네트워크 오류", t)
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
                } else {
                    Log.e("OrderClient", "응답 실패")
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Log.e("OrderClient", "네트워크 오류", t)
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
                } else {
                    Log.e("OrderClient", "응답 실패")
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Log.e("OrderClient", "네트워크 오류", t)
            }
        })
    }

    // 주문 삭제
    fun deleteOrder(id: Int) {
        apiService.deleteOrder(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("OrderClient", "주문이 삭제되었습니다.")
                } else {
                    Log.e("OrderClient", "응답 실패")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("OrderClient", "네트워크 오류", t)
            }
        })
    }
}
