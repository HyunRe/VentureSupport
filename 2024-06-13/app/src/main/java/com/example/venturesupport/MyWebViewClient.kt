package com.example.venturesupport

import android.util.Log
import android.webkit.JsResult
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.iamport.sdk.domain.IamportWebChromeClient
import com.iamport.sdk.domain.strategy.webview.IamPortMobileModeWebViewClient

/**
 * Iamport의 WebViewClient를 상속받아 URL 로드 제어를 구현합니다.
 * URL 로딩을 제어하기 위해 shouldOverrideUrlLoading 메서드를 오버라이드합니다.
 */
open class MyWebViewClient : IamPortMobileModeWebViewClient() {

    /**
     * 웹뷰가 새로운 URL을 로드할 때 호출됩니다.
     * @param view 현재 WebView 객체
     * @param request 요청된 WebResourceRequest 객체
     * @return URL 로딩을 처리할지 여부 반환
     */
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Log.i("MyWebViewClient", "updated webview url ${view?.url}")
        return super.shouldOverrideUrlLoading(view, request)
    }
}

/**
 * Iamport의 WebChromeClient를 상속받아 JavaScript의 confirm 대화상자를 처리합니다.
 * onJsConfirm 메서드를 오버라이드하여 대화상자 응답을 처리합니다.
 */
open class MyWebViewChromeClient : IamportWebChromeClient() {

    /**
     * JavaScript의 confirm 대화상자가 호출될 때 호출됩니다.
     * @param view 현재 WebView 객체
     * @param url 호출된 URL
     * @param message 확인 대화상자 메시지
     * @param result JsResult 객체: 사용자의 선택 결과를 전달합니다.
     * @return 대화상자 처리 결과 반환
     */
    override fun onJsConfirm(view: WebView, url: String, message: String, result: JsResult): Boolean {
        Log.i("MyWebViewChromeClient", "called this function")
        return super.onJsConfirm(view, url, message, result)
    }
}
