package com.example.venturesupport

import User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.util.copy
import com.example.venturesupport.databinding.EditmyprofileBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMyProfileActivity: AppCompatActivity() {
    private val binding: EditmyprofileBinding by lazy {
        EditmyprofileBinding.inflate(layoutInflater)
    }
    private var user: User? = null
    private lateinit var email: String
    private lateinit var name: String
    private lateinit var phoneNumber: String
    private lateinit var password: String
    private lateinit var confirmPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        user = intent.getParcelableExtra("user")

        binding.userEmailText.setText(user?.email, TextView.BufferType.EDITABLE)
        binding.userNameText.setText(user?.username, TextView.BufferType.EDITABLE)
        binding.userPhoneNumberText.setText(user?.phone, TextView.BufferType.EDITABLE)
        binding.userPasswordText.setText(user?.password, TextView.BufferType.EDITABLE)

        val id = user?.userId?: 0
        val latitude = user?.lat ?: 0.0
        val longitude = user?.lng ?: 0.0
        val role = user?.role ?: UserRole.COMPANY

        binding.editButton.setOnClickListener {
            email = if (binding.userEmail.text.isNullOrEmpty()) {
                user?.email.toString()
            } else {
                binding.userEmail.text.toString()
            }
            name = if (binding.userName.text.isNullOrEmpty()) {
                user?.username.toString()
            } else {
                binding.userName.text.toString()
            }
            phoneNumber = if (binding.userPhoneNumber.text.isNullOrEmpty()) {
                user?.phone.toString()
            } else {
                binding.userPhoneNumber.text.toString()
            }
            password = if (binding.userPassword.text.isNullOrEmpty()) {
                user?.password.toString()
            } else {
                binding.userPassword.text.toString()
            }
            confirmPassword = if (binding.userPassword2.text.isNullOrEmpty()) {
                user?.password.toString()
            } else {
                binding.userPassword2.text.toString()
            }

            // 유저 갱신
            if (password == confirmPassword) {
                updateUserInfo(id, email, name, phoneNumber, password, latitude, longitude, role)
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            } else {
                showErrorDialog()
            }
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("오류")
            .setMessage("비밀번호가 일치하지 않습니다.")
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun updateUserInfo(userId: Int, email: String, name: String, phoneNumber: String, password: String, latitude: Double, longitude: Double, role: UserRole) {
        val user = User(
            userId = userId,
            username = name,
            email = email,
            lat = latitude,
            lng = longitude,
            phone = phoneNumber,
            role = role,
            password = password
        )

        val call = RetrofitService.userService.updateUser(userId, user)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    Log.d("EditMyProfileActivity", "Response: $responseBody")
                } else {
                    Log.e("EditMyProfileActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("EditMyProfileActivity", "Network request failed", t)
            }
        })
    }
}