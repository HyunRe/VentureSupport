package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.AddvehicleinventoryBinding
import com.example.test2.databinding.SelectedorderBinding
import com.example.test2.databinding.SelectedorderlistBinding
import com.example.test2.databinding.SelectedorderlistItemBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddVehicleInventoryActivity: AppCompatActivity() {
    private val binding: AddvehicleinventoryBinding by lazy {
        AddvehicleinventoryBinding.inflate(layoutInflater)
    }
    private var intentOrder: Order? = null
    private var intentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        intentOrder = intent.getParcelableExtra("intentOrder")
        @Suppress("DEPRECATION")
        intentUser = intent.getParcelableExtra("intentUser")

        // 수정 필요
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