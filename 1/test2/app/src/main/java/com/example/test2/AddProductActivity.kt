package com.example.test2

import Product
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.AddproductBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductActivity: AppCompatActivity() {
    private val binding: AddproductBinding by lazy {
        AddproductBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.checkButton.setOnClickListener {
            val productNameEdit = binding.productNameEdit.text.toString()
            val productQuantityEdit = binding.productQuantityEdit.text.toString()
            val productPriceEdit = binding.productPriceEdit.text.toString()

            val product = Product(
                productId = null,
                productName = productNameEdit,
                productPrice = productPriceEdit.toDouble(),
                productQuantity = productQuantityEdit
            )

            createProduct(product)

            val intent = Intent(this, CreateOrderActivity::class.java)
            intent.putExtra("intentProduct", product)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun createProduct(product: Product) {
        val call = RetrofitService.productService.createProduct(product)
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val createdProduct = response.body()
                    println("Warehouse 생성 성공: $createdProduct")
                } else {
                    println("Warehouse 생성 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                println("네트워크 요청 실패: ${t.message}")
            }
        })
    }
}