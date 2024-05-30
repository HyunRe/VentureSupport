package com.example.loginvianaver.ui

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginvianaver.R

class NaverPaymentActivity : AppCompatActivity() {

    //lateinit var webview : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_naver_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        //webview = findViewById(R.id.webView)

        //setupWebView()
/*
        webview.settings.domStorageEnabled  = true
        val htmlContent = """
            <html>
            <head></head>
            <body>
                <input type="button" id="naverPayBtn" value="네이버페이 결제 버튼">
                <script src="https://nsp.pay.naver.com/sdk/js/naverpay.min.js"></script>
                <script>
                    var oPay = Naver.Pay.create({
                          "mode" : "production", // development or production
                          "clientId": "u86j4ripEt8LRfPGzQ8", // clientId
                          "chainId": "TDZSUHBoVGRFS2l" // chainId
                    });

                    //직접 만드신 네이버페이 결제버튼에 click Event를 할당하세요
                    var elNaverPayBtn = document.getElementById("naverPayBtn");

                    elNaverPayBtn.addEventListener("click", function() {
                        oPay.open({
                          "merchantUserKey": "가맹점 사용자 식별키",
                          "merchantPayKey": "가맹점 주문 번호",
                          "productName": "상품명을 입력하세요",
                          "totalPayAmount": "1000",
                          "taxScopeAmount": "1000",
                          "taxExScopeAmount": "0",
                          "returnUrl": "사용자 결제 완료 후 결제 결과를 받을 URL"
                        });
                    });
                </script>
            </body>
            </html>
        """.trimIndent()

        webview.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)*/
    }

    /*private fun setupWebView() {
        val webSettings: WebSettings = webview.settings
        webSettings.javaScriptEnabled = true
        webview.webViewClient = WebViewClient()
    }*/

}
