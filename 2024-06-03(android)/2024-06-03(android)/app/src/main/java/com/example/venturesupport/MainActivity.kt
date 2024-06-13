package com.example.venturesupport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // MapActivity로 이동하는 버튼 클릭 이벤트 핸들러
    fun onMapButtonClick(view: View) {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }
}
