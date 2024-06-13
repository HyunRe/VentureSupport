package com.example.venturesupport

import User
import android.content.Intent
import android.widget.Toast
import com.example.venturesupport.databinding.PaymentBinding
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
    /*기존 결제수단 정보 가져와 이용자가 그 중 하나 선택 시 해당 결제수단으로 결제*/
    //private lateinit var paymentAdapter: PaymentAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 이전 액티비티에서 사용자 정보 받아오기
        user = intent.getParcelableExtra("user") ?: error("사용자 정보가 없습니다.")

        /* // 임시 사용자 정보
        val ID = User(
            1, "ann", "user@admin.com",
            0.0, 1.1, "010-010-101",
            UserRole.DRIVER, "암호"
        )*/

        loadMyPayment(user)
        /*// RecyclerView 초기화
        paymentAdapter = PaymentAdapter { payment ->
            // 클릭한 결제 수단의 paymentId를 인텐트에 추가하여 ExpenseChartActivity로 전달
            val intent = Intent(this@PaymentActivity, ExpenseChartActivity::class.java)
            intent.putExtra("paymentId", payment.paymentId)
            startActivity(intent)
        }*/

        // 결제 버튼 클릭 시 이벤트 처리
        binding.PaymentInputButton.setOnClickListener {
            // 입력된 카드 번호 가져오기
            val cardNumber = binding.cardNumberInput.text.toString()

            // Payment 객체 생성 및 서버로 전송
            val payment = Payment(
                paymentId = 0, // 새로 생성하는 경우 ID는 서버에서 할당
                user = user, // 현재 사용자 정보
                paymentName = cardNumber
            )

            // 서버에 결제 수단 저장 요청
            savePayment(payment)
            returnPayment(payment) //전송 내용 확인용. 삭제해도 무방
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
        val call = RetrofitService.paymentService.getPaymentById(user.userId)
        call.enqueue(object : Callback<Payment> {
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if (response.isSuccessful) { // 서버 응답이 성공적으로 수신된 경우
                    val payment = response.body()
                    // 결제 정보를 화면에 표시합니다.
                    binding.payLongTextView.text = payment?.paymentName ?: "결제 수단 없음"
                    //paymentAdapter.setData(listOf(payment)) // 결제 정보를 RecyclerView에
                } else {
                    // 서버 응답이 실패한 경우 에러 메시지를 사용자에게 표시합니다.
                    Toast.makeText(this@PaymentActivity, "결제 수단 조회 실패",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Payment>, t: Throwable) {
                Log.e("PaymentActivity", "네트워크 오류", t) // 네트워크 오류 로그 출력
                // 사용자에게 알림
                Toast.makeText(this@PaymentActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }


    /**
     * 결제 정보를 서버에 저장하는 메서드입니다.
     * @param payment Payment - 저장할 결제 정보
     */
    private fun savePayment(payment: Payment) {
    // 결제 정보를 서버에 저장하는 API 호출
        val call = RetrofitService.paymentService.createPayment(payment)
        call.enqueue(object : Callback<Payment> {
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if (response.isSuccessful) { // 성공적으로 생성된 경우
                    val createdPayment = response.body()
                    Log.d("PaymentActivity", "Payment 생성 성공: $createdPayment") // 로그에 성공 메시지 출력
                // 사용자에게 알림
                    Toast.makeText(this@PaymentActivity, "결제 수단이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                // 지출 차트 액티비티를 시작하고 현재 액티비티를 종료하여 뒤로 가기 버튼을 눌렀을 때 이전 액티비티가 나타나지 않도록 설정
                    val intent = Intent(this@PaymentActivity, ExpenseChartActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else { // 생성에 실패한 경우
                    Log.e("PaymentActivity", "Payment 생성 실패: ${response.code()}") // 에러 로그 출력
                // 사용자에게 알림
                    Toast.makeText(this@PaymentActivity, "결제 수단 추가 실패", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<Payment>, t: Throwable) {
            Log.e("PaymentActivity", "네트워크 오류", t) // 네트워크 오류 로그 출력
            // 사용자에게 알림
            Toast.makeText(this@PaymentActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
         }
    })
    }


}
