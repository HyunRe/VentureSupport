package com.example.loginvianaver.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginvianaver.R
import com.iamport.sdk.BuildConfig
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.IamPortResponse
import com.iamport.sdk.data.sdk.PayMethod
import com.iamport.sdk.domain.core.Iamport
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.oauth.view.NidOAuthLoginButton

// MainActivity 클래스는 AppCompatActivity를 상속받아 네이버 로그인 기능을 제공
class MainActivity : AppCompatActivity() {

    // Activity가 생성될 때 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 엣지 투 엣지 화면 모드를 활성화
        setContentView(R.layout.activity_main) // 레이아웃 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // 시스템 바의 인셋을 가져와서 패딩 설정
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Iamport SDK 초기화
        Iamport.create(application)
        // 디버깅 모드에서 WebView 디버깅을 활성화
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
        }

        // 네이버 로그인 클라이언트 ID, Secret, Name을 가져옴
        val clientId = getString(R.string.OAUTH_CLIENT_ID)
        val clientSecret = getString(R.string.OAUTH_CLIENT_SECRET)
        val clientName = getString(R.string.OAUTH_CLIENT_NAME)

        // 네이버 로그인 SDK 초기화
        NaverIdLoginSDK.initialize(this, clientId, clientSecret, clientName)
        val btn = findViewById<NidOAuthLoginButton>(R.id.buttonOAuthLoginImg)
        // 네이버 로그인 버튼에 OAuthLoginCallback 설정
        btn.setOAuthLogin(object : OAuthLoginCallback {
            // 로그인 에러 발생 시 호출
            override fun onError(errorCode: Int, message: String) {
                Log.e("NaverLogin", "onError: $message")
            }

            // 로그인 실패 시 호출
            override fun onFailure(httpStatus: Int, message: String) {
                Log.e("NaverLogin", "onFailure: $message")
            }

            // 로그인 성공 시 호출
            override fun onSuccess() {
                Log.d("NaverLogin", "success")
                // HomeActivity로 이동하는 인텐트 생성 및 시작
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        })
    }
}
