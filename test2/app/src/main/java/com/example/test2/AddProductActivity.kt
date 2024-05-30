package com.example.test2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductActivity : AppCompatActivity() {

    private lateinit var productNameEditText: EditText
    private lateinit var productDescriptionEditText: EditText
    private lateinit var productQuantityEditText: EditText
    private lateinit var productPriceEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var productApi: ProductApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addproduct)

        productNameEditText = findViewById(R.id.product_name_edit)
        productDescriptionEditText = findViewById(R.id.product_description_edit)
        productQuantityEditText = findViewById(R.id.product_quantity_edit)
        productPriceEditText = findViewById(R.id.product_price_edit)
        saveButton = findViewById(R.id.check_button)


        saveButton.setOnClickListener {
            saveProduct()
        }
    }

    private fun saveProduct() {
        val name = productNameEditText.text.toString()
        val description = productDescriptionEditText.text.toString()
        val quantity = productQuantityEditText.text.toString().toIntOrNull()
        val price = productPriceEditText.text.toString().toDoubleOrNull()

        //if (name.isNotEmpty() && description.isNotEmpty() && quantity != null && price != null) {
            if (name.isNotEmpty() && quantity != null && price != null) {
                val newProduct = Product(
                    productId = null,
                    productName = name,
                    //productDescription = description,
                    productQuantity = quantity,
                    productPrice = price
                )

                // API를 통해 새 제품 추가
                productApi.createProduct(newProduct).enqueue(object : Callback<Product> {
                    override fun onResponse(call: Call<Product>, response: Response<Product>) {
                        if (response.isSuccessful) {
                            // 성공적으로 추가됨
                            // TODO: 성공 메시지 표시 또는 화면 닫기 등의 작업 수행
                        } else {
                            // 실패
                            // TODO: 실패 메시지 표시 또는 재시도 안내 등의 작업 수행
                        }
                    }

                    override fun onFailure(call: Call<Product>, t: Throwable) {
                        // 네트워크 오류 또는 예기치 않은 오류
                        // TODO: 오류 메시지 표시 또는 재시도 안내 등의 작업 수행
                    }
                })
            } else {
                // 입력 데이터가 유효하지 않은 경우 처리
                // TODO: 적절한 오류 메시지 표시 또는 경고 다이얼로그 표시 등의 작업 수행
            }
        }
    }

