package com.example.loginvianaver.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.iamport.sdk.domain.core.Iamport
import com.iamport.sdk.domain.utils.CONST

class MerchantReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            CONST.BROADCAST_FOREGROUND_SERVICE -> {
                // 포그라운드 서비스가 시작될 때 처리할 로직을 여기에 추가합니다.
            }
            CONST.BROADCAST_FOREGROUND_SERVICE_STOP -> {
                Iamport.failFinish() // 포그라운드 서비스가 종료될 때 처리할 로직
            }
        }
    }
}
