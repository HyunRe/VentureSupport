package com.example.myactivity.ui.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.R
import com.example.myactivity.data.model.Order
import com.example.myactivity.data.model.Product
import com.example.myactivity.data.network.RetrofitInstance
import com.example.myactivity.data.repository.OrderRepository
import com.example.myactivity.databinding.ActivityOrderBinding
import com.google.android.material.snackbar.Snackbar

class orderActivity : AppCompatActivity() {
    private var binding: ActivityOrderBinding? = null
    private val orderRepository = OrderRepository(RetrofitInstance.apiService)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        val toolbar = binding!!.toolbar
        setSupportActionBar(toolbar)
        val toolBarLayout = binding!!.toolbarLayout
        toolBarLayout.title = title
        val fab = binding!!.fab
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        btnSubmit.setOnClickListener {
            val order = createOrderFromInput()
            sendOrder(order)
        }
    }

    private fun createOrderFromInput(): Order {
        val products = listOf(
            Product(productId = 1, name = "Product1", quantity = 2),
            Product(productId = 2, name = "Product2", quantity = 3)
        )
        return Order(
            orderId = 0,
            customerName = edtCustomerName.text.toString(),
            address = edtAddress.text.toString(),
            products = products
        )
    }

    private fun sendOrder(order: Order) {
        // OrderRepository를 통해 서버로 전송
    }
}