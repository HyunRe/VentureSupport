package com.example.test2

import Order
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: HomeAdapter
    private val orderList = listOf<Order>() // 서버에서 받아온 데이터 리스트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = HomeAdapter(orderList) { order ->
            val intent = Intent(this, HomeDetailActivity::class.java)
            intent.putExtra("order", order)
            startActivity(intent)
        }

        binding.myorderlistRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.myorderlistRecyclerView.adapter = adapter

        binding.count.text = orderList.size.toString()
    }
}
