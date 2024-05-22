package com.example.venturesupport

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapbutton: Button = findViewById(R.id.mapbutton)
        val schedulerbutton: Button = findViewById(R.id.schedulerbutton)

        mapbutton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        schedulerbutton.setOnClickListener {
            val intent = Intent(this, SchedulerActivity::class.java)
            startActivity(intent)
        }
    }
}
