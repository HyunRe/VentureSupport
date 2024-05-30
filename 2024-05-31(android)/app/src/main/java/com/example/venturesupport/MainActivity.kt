package com.example.venturesupport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    // orders 변수 선언
    private val orders: ArrayList<Order> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 여기서 orders 변수에 값을 할당하거나 초기화할 수 있습니다.
    }

    // MapActivity로 이동하는 버튼 클릭 이벤트 핸들러
    fun onMapButtonClick(view: View) {
        val intent = Intent(this, MapActivity::class.java)
        // orders ArrayList를 intent에 추가
        intent.putExtra("orders", orders)
        startActivity(intent)
    }

    // SchedulerActivity로 이동하는 버튼 클릭 이벤트 핸들러
    fun onSchedulerButtonClick(view: View) {
        val intent = Intent(this, SchedulerActivity::class.java)
        // orders ArrayList를 intent에 추가
        intent.putExtra("orders", orders)
        startActivity(intent)
    }
}
