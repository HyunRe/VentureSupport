package com.example.test2

import Product
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
    // 인텐트에서 전달된 Order 객체와 User 객체를 저장
    private var intentOrder: Order? = null
    private var intentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 인텐트로부터 Order와 User 객체를 가져오기
        @Suppress("DEPRECATION")
        intentOrder = intent.getParcelableExtra("intentOrder")
        @Suppress("DEPRECATION")
        intentUser = intent.getParcelableExtra("intentUser")

        // 수정 필요
        binding.checkButton.setOnClickListener {
            // 사용자가 입력한 제품 정보 가져오기
            val productNameEdit = binding.productNameEdit.text.toString()
            val productQuantityEdit = binding.productQuantityEdit.text.toString()
            //val productPriceEdit = binding.productPriceEdit.text.toString()

            // 제품 객체 생성
            val product = Product(
                productId = null,
                productName = productNameEdit,
                productPrice = null, //productPriceEdit.toDouble(),
                productQuantity = productQuantityEdit
            )


            createProduct(product)

            // 제품 정보를 다음 액티비티로 전달하고 종료
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