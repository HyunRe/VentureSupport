package com.example.myactivity.client
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.myactivity.data.ApiClient
import com.example.myactivity.data.model.Payment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentApiService(private val context: Context) {

    private val apiService = ApiClient.paymentService

    // 모든 Payment 조회
    fun getAllPayments() {
        apiService.getAllPayments().enqueue(object : Callback<List<Payment>> {
            override fun onResponse(call: Call<List<Payment>>, response: Response<List<Payment>>) {
                if (response.isSuccessful) {
                    Log.d("PaymentApiService", "모든 Payment 조회 성공: ${response.body()}")
                    Toast.makeText(context, "모든 Payment를 성공적으로 조회했습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("PaymentApiService", "모든 Payment 조회 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "모든 Payment 조회에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Payment>>, t: Throwable) {
                Log.e("PaymentApiService", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 특정 Payment 조회
    fun getPaymentById(id: Int) {
        apiService.getPaymentById(id).enqueue(object : Callback<Payment> {
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if (response.isSuccessful) {
                    val payment = response.body()
                    Log.d("PaymentCli", "결제 조회 성공: $payment")
                    Toast.makeText(context, "결제 조회 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("PaymentCli", "결제 조회 실패")
                    Toast.makeText(context, "결제 조회 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Payment>, t: Throwable) {
                Log.e("PaymentApiService", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Payment 생성
    fun createPayment(payment: Payment) {
        apiService.createPayment(payment).enqueue(object : Callback<Payment> {
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if (response.isSuccessful) {
                    Log.d("PaymentApiService", "Payment 생성 성공: ${response.body()}")
                    Toast.makeText(context, "Payment가 성공적으로 생성되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("PaymentApiService", "Payment 생성 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Payment 생성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Payment>, t: Throwable) {
                Log.e("PaymentApiService", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Payment 업데이트
    fun updatePayment(id: Int, payment: Payment) {
        apiService.updatePayment(id, payment).enqueue(object : Callback<Payment> {
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if (response.isSuccessful) {
                    Log.d("PaymentApiService", "Payment 업데이트 성공: ${response.body()}")
                    Toast.makeText(context, "Payment 정보가 성공적으로 업데이트되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("PaymentApiService", "Payment 업데이트 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Payment 정보 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Payment>, t: Throwable) {
                Log.e("PaymentApiService", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Payment 삭제
    fun deletePayment(id: Int) {
        apiService.deletePayment(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("PaymentApiService", "Payment 삭제 성공")
                    Toast.makeText(context, "Payment가 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("PaymentApiService", "Payment 삭제 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Payment 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("PaymentApiService", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
