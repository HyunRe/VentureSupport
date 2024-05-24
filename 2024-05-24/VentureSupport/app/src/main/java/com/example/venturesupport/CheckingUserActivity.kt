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
    private var myprofileLists = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.checkButton.setOnClickListener {
            val email = binding.email.toString()
            val password = binding.password.toString()

            if (email == myprofileLists[0].email && password == myprofileLists[0].password) {
                val intent = Intent(this, EditMyProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                Toast.makeText(this, "이메일 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                showErrorDialog()
            }
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("오류")
            .setMessage("이메일 또는 비밀번호가 일치하지 않습니다.")
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}