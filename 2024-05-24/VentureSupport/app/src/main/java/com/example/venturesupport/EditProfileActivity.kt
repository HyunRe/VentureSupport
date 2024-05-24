package com.example.venturesupport

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.EditprofileBinding

class EditProfileActivity: AppCompatActivity() {
    private val binding: EditprofileBinding by lazy {
        EditprofileBinding.inflate(layoutInflater)
    }
    private var myprofileLists = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.userEmail.text = myprofileLists[0].email
        binding.userId.text = myprofileLists[0].user_id
        binding.userName.text = myprofileLists[0].user_name
        binding.userPhoneNumber.text = myprofileLists[0].phone

        binding.editButton.setOnClickListener {
            val intent = Intent(this, CheckingUserActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}