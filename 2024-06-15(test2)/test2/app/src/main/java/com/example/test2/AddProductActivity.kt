package com.example.test2

import Product
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.AddproductBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddProductActivity: AppCompatActivity() {
    private val binding: AddproductBinding by lazy {
        AddproductBinding.inflate(layoutInflater)
    }
    private lateinit var sharedPreferences: SharedPreferences
    private var intentCompany: Company? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        intentCompany = intent.getParcelableExtra("intentCompany")
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        binding.checkButton.setOnClickListener {
            val productNameEdit = binding.productNameEdit.text.toString()
            val productQuantityEdit = binding.productQuantityEdit.text.toString()
            val productPriceEdit = binding.productPriceEdit.text.toString()

            val product = Product(
                productId = null,
                productName = productNameEdit,
                productPrice = productPriceEdit.toInt(),
                productQuantity = productQuantityEdit,
            )

            // 기존의 제품 목록을 가져와서 새 제품을 추가합니다.
            val gson = Gson()
            val productListsJson = sharedPreferences.getString("productLists", null)
            val type = object : TypeToken<ArrayList<Product>>() {}.type
            val currentProductList: ArrayList<Product> = if (productListsJson != null) {
                gson.fromJson(productListsJson, type)
            } else {
                ArrayList()
            }
            currentProductList.add(product)

            // SharedPreferences에 productLists 값을 저장
            val editor = sharedPreferences.edit()
            editor.putString("productLists", gson.toJson(currentProductList))
            editor.apply()

            // product 전달
            val intent = Intent(this, CreateOrderActivity::class.java)
            intent.putExtra("intentCompany", intentCompany)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}