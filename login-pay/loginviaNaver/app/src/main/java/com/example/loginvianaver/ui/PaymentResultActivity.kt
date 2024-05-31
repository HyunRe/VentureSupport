package com.example.loginvianaver.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginvianaver.R
import com.example.loginvianaver.viewmodel.PaymentResultData
import com.iamport.sdk.data.sdk.IamPortResponse

class PaymentResultActivity : AppCompatActivity() {

    lateinit var resultMessage : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val impResponse = PaymentResultData.result66
        val resultText = if (isSuccess(impResponse)) "결제성공" else "결제실패"
        val color = if (isSuccess(impResponse)) R.color.md_green_200 else R.color.fighting


        resultMessage = findViewById(R.id.result_message)

        resultMessage.setTextColor(ContextCompat.getColor(this
        ,color))
    }

    private fun isSuccess(iamPortResponse: IamPortResponse?): Boolean {
        if (iamPortResponse == null) {
            return false
        }
        return iamPortResponse.success == true || iamPortResponse.imp_success == true
    }
}