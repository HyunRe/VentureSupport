package com.example.loginvianaver.viewmodel

import android.util.Log
import android.webkit.JsResult
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.iamport.sdk.domain.IamportWebChromeClient
import com.iamport.sdk.domain.strategy.webview.IamPortMobileModeWebViewClient

// IamPortMobileModeWebViewClient를 상속하여 URL 로딩 처리를 커스터마이징하는 클래스
open class MyWebViewClient : IamPortMobileModeWebViewClient() {

    // 웹 페이지의 URL 요청이 있을 때 호출되는 메서드
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        // 현재 URL을 로그에 기록
        Log.i("MyWebViewClient", "updated webview url ${view?.url}")
        // 기본 동작을 호출하여 URL 로딩을 처리
        return super.shouldOverrideUrlLoading(view, request)
    }

}

// IamportWebChromeClient를 상속하여 JavaScript 커스텀 처리 기능을 추가하는 클래스
open class MyWebViewChromeClient : IamportWebChromeClient() {

    // JavaScript의 confirm 메서드 호출 시 처리하는 메서드
    override fun onJsConfirm(view: WebView, url: String, message: String, result: JsResult): Boolean {
        // 로그에 호출 사실을 기록
        Log.i("MyWebViewChromeClient", "called this function")
        // 기본 동작을 호출하여 confirm 처리
        return super.onJsConfirm(view, url, message, result)
    }
}
