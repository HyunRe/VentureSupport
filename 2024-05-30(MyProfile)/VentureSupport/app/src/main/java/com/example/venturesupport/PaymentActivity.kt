package com.example.venturesupport

import User
import android.content.Intent
import android.widget.Toast
import com.example.venturesupport.databinding.PaymentBinding

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.WarehouseRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {
    private val binding: PaymentBinding by lazy {
        PaymentBinding.inflate(layoutInflater)
    }
    private lateinit var user: User

    val ID = User(1, "ann", "user@admin.com",
        0.0, 1.1, "010-010-101",
        UserRole.DRIVER, "암호")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.PaymentInputButton.setOnClickListener {
            // 결제 수단 창 이동 로직
            // 새로운 결제 수단을 추가하는 로직
            //val paymentName = binding.paymentNameInput.text.toString()
            //val cardName = binding.cardNameInput.text.toString()
            val cardNumber = binding.cardNumberInput.text.toString()

            // Payment 객체 생성 및 서버로 전송하는 로직 구현 필요
            val payment = Payment(
                paymentId = 0, // 새로 생성하는 경우 ID는 서버에서 할당
                user = ID, //user, // 실제 사용자 ID로 대체
                paymentName = cardNumber
            )

            // 서버로 결제 수단 저장 요청 전송
            //savePayment(payment)
            returnPayment(payment)
        }
    }

    private fun returnPayment(payment: Payment) {
        binding.payLongTextView.text = payment.paymentName
    }

    private fun savePayment(payment: Payment) {
        val call = RetrofitService.paymentService.createPayment(payment)
        call.enqueue(object : Callback<Payment> {
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if (response.isSuccessful) {
                    val createdPayment = response.body()
                    println("Payment 생성 성공: $createdPayment")
                    Toast.makeText(this@PaymentActivity, "결제 수단이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@PaymentActivity, ExpenseChartActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    println("Payment 생성 실패: ${response.code()}")
                    Toast.makeText(this@PaymentActivity, "결제 수단 추가 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Payment>, t: Throwable) {
                Toast.makeText(this@PaymentActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
