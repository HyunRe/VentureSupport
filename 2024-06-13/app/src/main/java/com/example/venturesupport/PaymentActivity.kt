package com.example.venturesupport

import User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.PaymentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 결제 정보를 입력받고 처리하는 액티비티입니다.
 */

class PaymentActivity : AppCompatActivity() {
    private val binding: PaymentBinding by lazy {
        PaymentBinding.inflate(layoutInflater)
    }
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 이전 액티비티에서 사용자 정보 받아오기
        user = intent.getParcelableExtra<User>("user") ?: error("사용자 정보가 없습니다.")

        loadMyPayment(user)

        // 결제 버튼 클릭 시 이벤트 처리
        binding.PaymentInputButton.setOnClickListener {
            val cardNumber = binding.cardNumberInput.text.toString()

            // Payment 객체 생성 및 서버로 전송
            val payment = Payment(
                paymentId = null, // 새로 생성하는 경우 ID는 서버에서 할당
                user = user,
                paymentName = cardNumber
            )

            // 서버에 결제 수단 저장 요청
            savePayment(payment)
            returnPayment(payment)  //전송 내용 확인용. 삭제해도 무방

        }
    }

    /**
     * 결제 정보를 화면에 표시하는 메서드입니다.
     * @param payment Payment - 표시할 결제 정보
     */
    private fun returnPayment(payment: Payment) {
        binding.payLongTextView.text = payment.paymentName
    }


    private fun loadMyPayment(user: User) {
        // 내 기존 결제 정보를 화면에 표시하는 API 호출

        val call = RetrofitService.paymentService.getAllPayments()
        call.enqueue(object : Callback<List<Payment>> {
            override fun onResponse(call: Call<List<Payment>>, response: Response<List<Payment>>) {
                if (response.isSuccessful) {
                    val payments = response.body()
                    if (!payments.isNullOrEmpty()) {
                        //기존 결제정보들을 payments[] 리스트에 넣어 출력
                        val payment = payments[0]
                        // 결제 정보를 화면에 표시합니다.

                        binding.payLongTextView.text = payment.paymentName ?: "결제 수단 없음"
                    } else {
                        binding.payLongTextView.text = "결제 수단 없음"
                    }
                } else {
                    Log.e("PaymentActivity", "네트워크 오류")
                    Toast.makeText(this@PaymentActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Payment>>, t: Throwable) {
                Log.e("PaymentActivity", "API 호출 실패", t)
                Toast.makeText(this@PaymentActivity, "API 호출 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * 결제 정보를 서버에 저장하는 메서드입니다.
     * @param payment Payment - 저장할 결제 정보
     */
    private fun savePayment(payment: Payment) {
        val call = RetrofitService.paymentService.createPayment(payment)
        // 결제 정보를 서버에 저장하는 API 호출

        call.enqueue(object : Callback<Payment> {
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if (response.isSuccessful) {
                    val createdPayment = response.body()
                    Log.d("PaymentActivity", "Payment 생성 성공: $createdPayment")
                    // 지출 차트 액티비티를 시작하고 현재 액티비티를 종료하여 뒤로 가기 버튼을 눌렀을 때 이전 액티비티가 나타나지 않도록 설정
                    val intent = Intent(this@PaymentActivity, ExpenseChartActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("PaymentActivity", "Payment 생성 실패: ${response.code()}")
                    Toast.makeText(this@PaymentActivity, "결제 수단 추가 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Payment>, t: Throwable) {
                Log.e("PaymentActivity", "네트워크 오류", t)
                Toast.makeText(this@PaymentActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
