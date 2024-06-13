package com.example.venturesupport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.Supplier

class SchedulerViewmodel : ViewModel() {
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    fun loadOrdersByUserId(userId: Int) {
        RetrofitService.orderService.getOrdersByUserId(userId).enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    // 서버 응답이 성공적일 경우, 데이터를 정렬하여 LiveData에 설정
                    _orders.value = response.body()?.sortedBy { it.date }
                } else {
                    // 서버 응답이 실패일 경우 샘플 오더 데이터 설정
                    _orders.value = getSampleOrders()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                // 네트워크 오류 또는 기타 실패 시 샘플 오더 데이터 설정
                _orders.value = getSampleOrders()
            }
        })
    }

    private fun getSampleOrders(): List<Order> {
        return listOf(
            Order(
                orderId = 1,
                date = "2024-06-13",
                product = Product(name = "Sample Product 1"),
                supplier = Supplier(name = "Sample Supplier 1"),
                company = Company(name = "Sample Company 1"),
                salary = 50000.0,
                totalAmount = 100000.0
            ),
            Order(
                orderId = 2,
                date = "2024-06-14",
                product = Product(name = "Sample Product 2"),
                supplier = Supplier(name = "Sample Supplier 2"),
                company = Company(name = "Sample Company 2"),
                salary = 60000.0,
                totalAmount = 150000.0
            )
        )
    }

}
