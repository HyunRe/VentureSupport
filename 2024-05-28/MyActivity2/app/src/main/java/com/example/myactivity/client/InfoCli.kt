package com.example.myactivity.client


import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.myactivity.data.ApiClient
import com.example.myactivity.data.model.ProductInformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductInformationClient(private val context: Context) {
    private val apiService = ApiClient.infoService

    //private val productInformationService = RetrofitClient.createService(ProductInformationService::class.java)

    // 모든 제품 정보 조회
    fun getAllProductInformation() {
        apiService.getAllProductInformation().enqueue(object : Callback<List<ProductInformation>> {
            override fun onResponse(call: Call<List<ProductInformation>>, response: Response<List<ProductInformation>>) {
                if (response.isSuccessful) {
                    val productInformationList = response.body()
                    Log.d("ProductInformationClient", "모든 제품 정보 조회 성공: ${response.body()}")
                    Toast.makeText(context, "모든 제품 정보를 성공적으로 조회했습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ProductInformationClient", "모든 제품 정보 조회 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "모든 제품 정보 조회에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ProductInformation>>, t: Throwable) {
                Log.e("ProductInformationClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 특정 제품 정보 조회
    fun getProductInformationById(orderId: Int, productId: Int, userId: Int) {
        apiService.getProductInformationById(orderId, productId, userId).enqueue(object : Callback<ProductInformation> {
            override fun onResponse(call: Call<ProductInformation>, response: Response<ProductInformation>) {
                if (response.isSuccessful) {
                    val productInformation = response.body()
                    Log.d("ProductInformationClient", "제품 정보 조회 성공: ${response.body()}")
                    Toast.makeText(context, "제품 정보를 성공적으로 조회했습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ProductInformationClient", "제품 정보 조회 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "제품 정보 조회에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductInformation>, t: Throwable) {
                Log.e("ProductInformationClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 제품 정보 생성
    fun createProductInformation(productInformation: ProductInformation) {
        apiService.createProductInformation(productInformation).enqueue(object : Callback<ProductInformation> {
            override fun onResponse(call: Call<ProductInformation>, response: Response<ProductInformation>) {
                if (response.isSuccessful) {
                    val createdProductInformation = response.body()
                    Log.d("ProductInformationClient", "제품 정보 생성 성공: ${response.body()}")
                    Toast.makeText(context, "제품 정보가 성공적으로 생성되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ProductInformationClient", "제품 정보 생성 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "제품 정보 생성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductInformation>, t: Throwable) {
                Log.e("ProductInformationClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 제품 정보 업데이트
    fun updateProductInformation(productInformation: ProductInformation) {
        apiService.updateProductInformation(productInformation).enqueue(object : Callback<ProductInformation> {
            override fun onResponse(call: Call<ProductInformation>, response: Response<ProductInformation>) {
                if (response.isSuccessful) {
                    val updatedProductInformation = response.body()
                    Log.d("ProductInformationClient", "제품 정보 업데이트 성공: ${response.body()}")
                    Toast.makeText(context, "제품 정보가 성공적으로 업데이트되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ProductInformationClient", "제품 정보 업데이트 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "제품 정보 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductInformation>, t: Throwable) {
                Log.e("ProductInformationClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 제품 정보 삭제
    fun deleteProductInformation(orderId: Int, productId: Int, userId: Int) {
        apiService.deleteProductInformation(orderId, productId, userId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("ProductInformationClient", "제품 정보 삭제 성공")
                    Toast.makeText(context, "제품 정보가 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ProductInformationClient", "제품 정보 삭제 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "제품 정보 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("ProductInformationClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
