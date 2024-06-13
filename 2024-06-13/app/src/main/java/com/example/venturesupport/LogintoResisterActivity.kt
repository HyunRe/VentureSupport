package com.example.venturesupport

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.LogintoresisterBinding

/**
 * 로그인 또는 회원가입을 선택할 수 있는 화면입니다.
 */
class LogintoResisterActivity: AppCompatActivity() {
    private val binding: LogintoresisterBinding by lazy {
        LogintoresisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 네이버 로그인 버튼 클릭 리스너 설정
        binding.btnNaver.setOnClickListener {
            // TODO: 네이버 로그인 구현
        }

        // 회원가입 버튼 클릭 리스너 설정
        binding.btnRsesister.setOnClickListener {
            val intent = Intent(this, ResisterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}
