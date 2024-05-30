package com.example.test2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class HomeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val order = intent.getParcelableExtra<Order>("order")

        order?.let {
            binding.productName.text = it.product.productName
            binding.productDescription.text = it.product.productDescription // 가정: productDescription 필드가 존재함
            binding.productQuantity.text = it.totalAmount.toString()
            binding.productPrice.text = it.salary.toString()
        }
    }
}
