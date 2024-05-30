package com.example.test2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VehicleInventorylistActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VehicleInventorylistAdapter
    private lateinit var floatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vehicleinventorylist)

        recyclerView = findViewById(R.id.myorderlist_recyclerView)
        floatingActionButton = findViewById(R.id.floatingActionButton)

        // Initialize the RecyclerView with dummy data or fetch from your data source
        val products = listOf(
            Product(
                productId = 1,
                name = "Vehicle A",
                price = 10000.0,
                quantity = 10
            ),
            Product(
                productId = 2,
                name = "Vehicle B",
                price = 20000.0,
                quantity = 5
            ),
            // Add more products here
        )

        adapter = VehicleInventorylistAdapter(products)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }
    }
}

