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

        // 제품 입력 버튼 클릭 리스너 설정
        binding.checkButton.setOnClickListener {
            // 사용자가 입력한 제품 정보 가져오기
            val productNameEdit = binding.productNameEdit.text.toString() // 제품 이름 문자열 가져오기
            val productQuantityEdit = binding.productQuantityEdit.text.toString() // 제품 수량 문자열 가져오기
            val productPriceEdit = binding.productPriceEdit.text.toString() // 제품 가격 문자열 가져오기

            // Product 객체 생성
            val product = Product(
                productId = null, // 서버에서 생성된 ID를 받기 위해 null로 설정
                productName = productNameEdit, // 제품 이름
                productPrice = productPriceEdit.toDouble(), // 제품 가격 (문자열을 실수로 변환)
                productQuantity = productQuantityEdit // 제품 수량 (문자열을 정수로 변환)
            )

            // 제품 생성 함수 호출
            createProduct(product)

            // 새 액티비티로 이동하여 제품 정보를 전달
            val intent = Intent(this, CreateOrderActivity::class.java)
            intent.putExtra("intentProduct", product) // 제품 객체를 인텐트에 추가
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }
    }

    // 제품 생성 API 호출 함수
    private fun createProduct(product: Product) {
        val call = RetrofitService.productService.createProduct(product) // 제품 생성 API 호출
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val createdProduct = response.body() // 생성된 제품 정보 추출
                    println("제품 생성 성공: $createdProduct") // 로그 출력
                } else {
                    println("제품 생성 실패: ${response.code()}") // 실패 로그 출력
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                println("네트워크 요청 실패: ${t.message}") // 네트워크 오류 로그 출력
            }
        })
    }
}