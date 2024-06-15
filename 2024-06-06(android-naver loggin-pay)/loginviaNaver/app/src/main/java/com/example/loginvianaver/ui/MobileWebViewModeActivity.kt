package com.example.loginvianaver.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginvianaver.R
import com.example.loginvianaver.viewmodel.MyWebViewChromeClient
import com.example.loginvianaver.viewmodel.MyWebViewClient
import com.example.loginvianaver.viewmodel.ViewModel
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.domain.core.Iamport
import com.iamport.sdk.domain.utils.EventObserver

class MobileWebViewModeActivity : AppCompatActivity() {

    private val viewModel: ViewModel by viewModels()
    private var createdView = false

    lateinit var webview : WebView
    lateinit var normalmodeButton : Button
    private var webmode : View = findViewById(R.id.webview_mode_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 엣지 투 엣지 기능 활성화
        setContentView(R.layout.activity_web_view_mode) // 웹뷰 모드 레이아웃 설정
        // 윈도우 인셋 처리: 시스템 바 여백을 처리하여 웹뷰의 패딩을 설정합니다.
        ViewCompat.setOnApplyWindowInsetsListener(webmode) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        createdView = true // 뷰가 생성됨을 표시


    }

    override fun onStart() {
        // 뷰가 생성되지 않았으면 반환
        if (!createdView) {
            return
        }

        // 웹뷰 설정
        webview?.let {
            it.webViewClient = MyWebViewClient() // 웹뷰 클라이언트 설정: 웹 로딩 URL 변경, 페이지 로드 로직 정의
            it.webChromeClient = MyWebViewChromeClient() // 웹 크롬 클라이언트 설정: 웹 페이지의 클라이언트 로직 정의


            // 아임포트 URL 로딩 리스너 설정: URL 변경 시 로그 출력
            Iamport.mobileWebModeShouldOverrideUrlLoading()?.observe(this, EventObserver { uri ->
                Log.i("SAMPLE", "changed url :: $uri") // URL 변경 로그 출력
            })

            it.loadUrl("https://pay-demo.iamport.kr") // 아임포트 데모 페이지 로드
            Iamport.pluginMobileWebSupporter(it) // 아임포트 플러그인 지원 활성화
            createdView = false // 뷰 생성 완료 표시
        }

        // 일반 모드 버튼 클릭 리스너 설정: 버튼 클릭 시 아임포트 종료 및 백 스택 팝 처리
        normalmodeButton?.setOnClickListener {
            Iamport.close() // 아임포트 종료
            popBackStack() // 백 스택 팝
        }
        super.onStart()
    }

    /**
     * 백 스택 팝 메서드
     * 백 스택에서 현재 액티비티를 제거합니다.
     */
    fun popBackStack() {
        runCatching {
            popBackStack()
        }.onFailure {
        }
    }
}