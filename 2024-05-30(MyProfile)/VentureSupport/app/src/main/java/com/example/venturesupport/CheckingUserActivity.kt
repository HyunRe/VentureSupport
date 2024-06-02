package com.example.venturesupport

import User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.CheckinguserBinding

// CheckingUserActivity 클래스: 사용자의 이메일과 비밀번호를 확인하여 해당하는 프로필 수정 화면으로 이동시키는 액티비티
class CheckingUserActivity: AppCompatActivity() {

    private val binding: CheckinguserBinding by lazy {
        CheckinguserBinding.inflate(layoutInflater)
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

        // 로그인 확인 버튼에 클릭 리스너 설정
        binding.checkButton.setOnClickListener {
            // 사용자가 입력한 이메일과 비밀번호를 변수email, password에 저장
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()

            // 입력한 이메일과 비밀번호가 사용자 정보와 일치하는지 확인
            if (email == user?.email && password == user?.password) {
                // 일치하면 프로필 수정 화면으로 이동
                val intent = Intent(this, EditMyProfileActivity::class.java).apply {
                    putExtra("user", user) // 사용자 정보를 인텐트에 추가
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // 새로운 태스크로 액티비티 시작
                }
                startActivity(intent) //시작 선언문(인텐트 내 유저 정보 바탄)
                finish() // 현재 액티비티 종료
            } else {
                // 일치하지 않으면 오류 메시지(유저 표시 토스트) 출력 및 오류 다이얼로그 표시
                Toast.makeText(this, "이메일 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                showErrorDialog()
            }
        }
    }

    // showErrorDialog 함수: 이메일 또는 비밀번호가 일치하지 않을 때 오류 다이얼로그를 표시
    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("오류") // 다이얼로그 제목 설정
            .setMessage("이메일 또는 비밀번호가 일치하지 않습니다.") // 다이얼로그 메시지 설정
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss() // 확인 버튼 클릭 시 다이얼로그 닫기
            }
            .show() // 다이얼로그 표시
    }
}
