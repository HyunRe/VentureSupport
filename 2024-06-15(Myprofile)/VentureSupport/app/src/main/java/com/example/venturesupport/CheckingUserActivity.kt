package com.example.venturesupport

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.CheckinguserBinding

class CheckingUserActivity: AppCompatActivity() {
    private val binding: CheckinguserBinding by lazy {
        CheckinguserBinding.inflate(layoutInflater)
    }
    private var intentUser: User? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentUser = intent.getParcelableExtra("intentUser")

        binding.checkButton.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()

            if (email == intentUser?.email && password == intentUser?.userPassword) {
                val intent = Intent(this, EditMyProfileActivity::class.java).apply {
                    putExtra("intentUser", intentUser)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "이메일 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                showErrorDialog()
            }
        }
    }

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
