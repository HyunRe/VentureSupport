package com.example.myactivity.client
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.myactivity.data.ApiClient
import com.example.myactivity.data.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductCli(private val context: Context) {

    private val apiService = ApiClient.productService

    // 모든 제품 조회
    fun getAllProducts() {
        apiService.getAllProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val productList = response.body()
                    Log.d("ProductClient", "모든 제품: $productList")
                    Toast.makeText(context, "모든 제품을 불러왔습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ProductClient", "응답 실패")
                    Toast.makeText(context, "제품을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("ProductClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 제품 ID로 특정 제품 조회
    fun getProductById(id: Int) {
        apiService.getProductById(id).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val product = response.body()
                    Log.d("ProductClient", "조회된 제품: $product")
                    Toast.makeText(context, "제품을 불러왔습니다: $product", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ProductClient", "응답 실패")
                    Toast.makeText(context, "제품을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.e("ProductClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 새로운 제품 생성
    fun createProduct(product: Product) {
        apiService.createProduct(product).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val createdProduct = response.body()
                    Log.d("ProductClient", "생성된 제품: $createdProduct")
                    Toast.makeText(context, "제품이 생성되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ProductClient", "응답 실패")
                    Toast.makeText(context, "제품 생성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.e("ProductClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 기존 제품 업데이트
    fun updateProduct(id: Int, product: Product) {
        apiService.updateProduct(id, product).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val updatedProduct = response.body()
                    Log.d("ProductClient", "업데이트된 제품: $updatedProduct")
                    Toast.makeText(context, "제품이 업데이트되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ProductClient", "응답 실패")
                    Toast.makeText(context, "제품 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.e("ProductClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 제품 삭제
    fun deleteProduct(id: Int) {
        apiService.deleteProduct(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("ProductClient", "제품이 삭제되었습니다.")
                    Toast.makeText(context, "제품이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ProductClient", "응답 실패")
                    Toast.makeText(context, "제품 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("ProductClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
