package com.example.feapp.ui.confirm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myactivity.data.model.Order
import com.example.myactivity.data.repository.OrderRepository

class ConfirmPageViewModel : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    private val orderRepository = OrderRepository()



    init {
        loadOrders()
    }

    private suspend fun loadOrders() {
        // 비동기로 데이터를 가져오는 로직 추가
        _orders.value = orderRepository.getOrders()
    }
}
