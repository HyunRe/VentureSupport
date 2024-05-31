package com.example.venturesupport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SchedulerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SchedulerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scheduler)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Intent에서 주문 정보를 받아옴
        val orders = intent?.extras?.getSerializable("orders") as? ArrayList<Order> ?: ArrayList()

        // RecyclerView에 어댑터 설정
        adapter = SchedulerAdapter(orders)
        recyclerView.adapter = adapter
    }
}
