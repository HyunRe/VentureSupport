//confirm에서 선택 시 표시될 블록 목록형 간략화 원장 출력 참고용 코드
package com.example.myactivity.ui.confirm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.R
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val orderId = intent.getIntExtra("ORDER_ID", -1)
        val customerName = intent.getStringExtra("CUSTOMER_NAME") ?: ""
        val address = intent.getStringExtra("ADDRESS") ?: ""

        orderIdTextView.text = orderId.toString()
        customerNameTextView.text = customerName
        addressTextView.text = address
    }
}
