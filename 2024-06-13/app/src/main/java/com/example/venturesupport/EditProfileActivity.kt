package com.example.venturesupport

import User
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.EditprofileBinding

// EditProfileActivity 클래스: 사용자가 자신의 프로필을 확인하고 수정할 수 있는 화면을 제공하는 액티비티
class EditProfileActivity: AppCompatActivity() {

    private val binding: EditprofileBinding by lazy {
        EditprofileBinding.inflate(layoutInflater)
    }

    // user 변수: 인텐트에서 전달받은 사용자 정보를 저장
    private var user: User? = null

    // onCreate 함수: 액티비티 생성 시 호출되며, 초기 설정을 담당
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // 액티비티의 뷰를 설정

        // 사용자 정보를 인텐트에서 가져옴 (이전 API 사용으로 경고 억제)
        @Suppress("DEPRECATION")
        user = intent.getParcelableExtra("user")

        // 바인딩된 UI 요소에 사용자 정보 설정
        binding.userEmail.text = user?.email
        binding.userId.text = user?.userId.toString()
        binding.userName.text = user?.username
        binding.userPhoneNumber.text = user?.phone

        // 수정 버튼 클릭 리스너 설정
        binding.editButton.setOnClickListener {
            // CheckingUserActivity로 이동하는 인텐트 생성 및 시작
            val intent = Intent(this, CheckingUserActivity::class.java)
            intent.putExtra("user", user) // 사용자 정보를 인텐트에 추가
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // 새로운 태스크로 액티비티 시작
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }

        // 로그아웃 버튼 클릭 리스너 설정
        binding.logoutButton.setOnClickListener {
            // MainActivity로 이동하는 인텐트 생성 및 시작
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // 새로운 태스크로 액티비티 시작
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }
    }
}
