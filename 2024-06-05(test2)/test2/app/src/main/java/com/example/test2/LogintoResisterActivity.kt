package com.example.test2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.LogintoresisterBinding

class LogintoResisterActivity: AppCompatActivity() {
    private val binding: LogintoresisterBinding by lazy {
        LogintoresisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 네이버 로그인
        binding.btnNaver.setOnClickListener {

        }

        binding.btnRsesister.setOnClickListener {
            val intent = Intent(this, ResisterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}