package com.example.test2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.StartBinding

/**
 * 앱 시작 화면을 위한 액티비티 클래스입니다.
 */
class StartActivity: AppCompatActivity() {
    // 바인딩 변수: 레이아웃과 연결하여 UI 요소에 접근할 수 있도록 합니다.
    private val binding: StartBinding by lazy {
        StartBinding.inflate(layoutInflater)
    }

    /**
     * 액티비티가 생성될 때 호출됩니다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 로그인 화면으로 이동하기 위한 인텐트를 생성합니다.
        val intent = Intent(this, LoginActivity::class.java)

        // 로그인 화면으로 이동합니다.
        startActivity(intent)

        // 현재 액티비티 종료
        finish()
    }
}
