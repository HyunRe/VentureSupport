package com.example.venturesupport

import User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.EditprofileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity: AppCompatActivity() {
    private val binding: EditprofileBinding by lazy {
        EditprofileBinding.inflate(layoutInflater)
    }
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        user = intent.getParcelableExtra("user")

        binding.userEmail.text = user?.email
        binding.userId.text = user?.userId.toString()
        binding.userName.text = user?.username
        binding.userPhoneNumber.text = user?.phone

        // 유저 조회
        binding.editButton.setOnClickListener {
            val intent = Intent(this, CheckingUserActivity::class.java)
            intent.putExtra("user", user)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        binding.logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}