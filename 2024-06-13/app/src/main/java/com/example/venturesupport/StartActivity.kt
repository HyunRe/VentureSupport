package com.example.venturesupport

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.StartBinding
//로딩용 첫 화면
class StartActivity: AppCompatActivity() {
    private val binding: StartBinding by lazy {
        StartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

