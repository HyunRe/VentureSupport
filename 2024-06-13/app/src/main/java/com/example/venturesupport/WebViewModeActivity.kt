package com.example.venturesupport

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.venturesupport.R
import com.example.venturesupport.PaymentResultData
import com.example.venturesupport.ViewModel
import com.google.gson.GsonBuilder
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.IamPortResponse
import com.iamport.sdk.domain.core.ICallbackPaymentResult
import com.iamport.sdk.domain.core.Iamport
import com.iamport.sdk.domain.utils.Event
// 결제를 위한 WebViewModeActivity 클래스, AppCompatActivity를 상속
class WebViewModeActivity : AppCompatActivity() {

    // ViewModel 인스턴스 생성
    private val viewModel: ViewModel by viewModels()
    // IamPortRequest 객체
    private var request: IamPortRequest? = null

    // WebView와 Button UI 요소를 선언
    lateinit var webview : WebView
    lateinit var normalmodeButton : Button
    // webmode View 요소를 findViewById로 초기화
    private var webmode : View = findViewById(R.id.webview_mode_fragment)

    // Activity가 생성될 때 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 엣지 투 엣지 화면 모드를 활성화
        setContentView(R.layout.activity_web_view_mode) // 레이아웃 설정
        ViewCompat.setOnApplyWindowInsetsListener(webmode) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 데이터 초기화
        initData()
        // IamPortRequest 객체 생성
        request = viewModel.createIamPortRequest()
    }

    // 데이터가 시작될 때 호출되는 메서드
    private fun onStartData() {
        // ViewModel에서 사용자 코드를 가져옴
        val userCode = viewModel.userCode
        // request가 null이 아닌 경우
        request?.let { request ->

            // WebView를 초기화
            webview.let {

                // 뒤로가기 버튼 콜백을 추가
                this.onBackPressedDispatcher.addCallback(this, backPressCallback)

                // 결제 시작, 콜백 리스너를 통해 결제 결과를 처리
                Iamport.payment(userCode, webviewMode = it, iamPortRequest = request, paymentResultCallback = { it ->
                    // 결제 완료 후 결과 콜백을 로그에 출력
                    callBackListener.result(it)
                })

                // request를 null로 설정하여 reload 방지
                this.request = null
            }
        }

        // 일반 모드 버튼 클릭 리스너 설정
        normalmodeButton?.setOnClickListener {
            Iamport.close()
            popBackStack()
        }
    }

    // UI 요소를 초기화하는 메서드
    private fun initData(){
        webview = findViewById(R.id.webview)
        normalmodeButton = findViewById(R.id.normalmode_button)
    }

    // 결제 결과를 처리하는 콜백 리스너
    private val callBackListener = object : ICallbackPaymentResult {
        override fun result(iamPortResponse: IamPortResponse?) {
            // 결제 결과를 JSON 형식으로 변환하여 로그에 출력
            val resJson = GsonBuilder().setPrettyPrinting().create().toJson(iamPortResponse)
            Log.i("SAMPLE", "결제 결과 콜백\n$resJson")
            // PaymentResultData에 결과 저장
            PaymentResultData.result66 = iamPortResponse

            // 결과가 null이 아닌 경우 ViewModel의 LiveData에 결과 게시
            if (iamPortResponse != null) {
                viewModel.resultCallback.postValue(Event(iamPortResponse))
            }
        }
    }

    // 뒤로가기 스택을 관리하는 메서드
    fun popBackStack() {
        runCatching {
            // (activity as MainActivity).popBackStack()을 호출하여 뒤로가기 스택 관리
        }.onFailure {
            Log.e("WebViewMode", "돌아갈 수 없습니다.")
        }
    }

    // 뒤로가기 버튼 콜백
    private val backPressCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            remove() // 콜백 제거
            popBackStack() // 뒤로가기 스택 관리
        }
    }

}