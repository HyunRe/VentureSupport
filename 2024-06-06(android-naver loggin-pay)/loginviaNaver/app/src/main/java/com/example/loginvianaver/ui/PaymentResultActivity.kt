package com.example.loginvianaver.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginvianaver.R
import com.example.loginvianaver.service.ApiClient
import com.example.loginvianaver.viewmodel.PaymentResultData
import com.example.loginvianaver.viewmodel.ViewModel
import com.iamport.sdk.data.sdk.IamPortResponse
import com.iamport.sdk.data.sdk.Payment
import com.navercorp.nid.NaverIdLoginSDK
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class PaymentResultActivity : AppCompatActivity() {

    lateinit var resultMessage : TextView

    private val viewModel: ViewModel by viewModels()// 뷰모델 인스턴스 생성

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 엣지 투 엣지 모드 활성화
        setContentView(R.layout.activity_payment_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 결제 응답 데이터 가져오기
        val impResponse = PaymentResultData.result66
        val resultText = if (isSuccess(impResponse)) "결제성공" else "결제실패"
        val color = if (isSuccess(impResponse)) R.color.md_green_200 else R.color.fighting

        // 네이버 로그인 액세스 토큰 가져오기
        val accessToken = NaverIdLoginSDK.getAccessToken().toString()

        // 사용자 정보 요청 및 결제 정보 등록
        viewModel.getUserInfo(accessToken,this@PaymentResultActivity){
            if(isSuccess(impResponse)){
                val payment = com.example.loginvianaver.modell.Payment(generateRandomLongInRange(0,1000000).toInt(),it!!,"test")
                val sendDatatPayment = ApiClient.apiService.createPayment(payment)
                sendDatatPayment.enqueue(object : Callback<com.example.loginvianaver.modell.Payment>{
                    override fun onResponse(
                        p0: Call<com.example.loginvianaver.modell.Payment>,
                        p1: Response<com.example.loginvianaver.modell.Payment>
                    ) {
                        // 응답 처리
                    }

                    override fun onFailure(
                        p0: Call<com.example.loginvianaver.modell.Payment>,
                        p1: Throwable
                    ) {
                        //오류 처리
                    }

                })
            }
        }

        resultMessage = findViewById(R.id.result_message)

        // 결과 메시지 텍스트 색상 설정
        resultMessage.setTextColor(ContextCompat.getColor(this
        ,color))
    }

    // 결제 성공 여부 확인 함수
    private fun isSuccess(iamPortResponse: IamPortResponse?): Boolean {
        if (iamPortResponse == null) {
            return false
        }
        return iamPortResponse.success == true || iamPortResponse.imp_success == true
    }

    // 임의의 Long 값 생성 함수
    fun generateRandomLongInRange(min: Long, max: Long): Long {
        return Random.nextLong(min, max)
    }
}