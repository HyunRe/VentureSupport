package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.StartBinding

@Suppress("DEPRECATION")
class StartActivity: AppCompatActivity() {
    private val binding: StartBinding by lazy {
        StartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000) // 1초 후 인트로 실행
    }
}

