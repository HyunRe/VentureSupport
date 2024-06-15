package com.example.venturesupport

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.EditprofileBinding

class EditProfileActivity: AppCompatActivity() {
    private val binding: EditprofileBinding by lazy {
        EditprofileBinding.inflate(layoutInflater)
    }
    private var intentUser: User? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentUser = intent.getParcelableExtra("intentUser")

        binding.userEmail.text = intentUser?.email
        binding.userId.text = intentUser?.userId.toString()
        binding.userName.text = intentUser?.username
        binding.userPhoneNumber.text = intentUser?.userPhoneNumber

        binding.editButton.setOnClickListener {
            val intent = Intent(this, CheckingUserActivity::class.java)
            intent.putExtra("intentUser", intentUser)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        binding.logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // 인텐트 값 필요
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
