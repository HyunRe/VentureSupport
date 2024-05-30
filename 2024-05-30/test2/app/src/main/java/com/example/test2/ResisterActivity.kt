package com.example.test2

import User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.RegisterBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResisterActivity: AppCompatActivity() {
    private val binding: RegisterBinding by lazy {
        RegisterBinding.inflate(layoutInflater)
    }
    private var selectedRole: UserRole? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            selectedRole = when (checkedId) {
                binding.radioButtonCompany.id -> UserRole.COMPANY
                binding.radioButtonDriver.id -> UserRole.DRIVER
                else -> null
            }

            selectedRole?.let {
                Log.d("LoginActivity", "Selected role: $it")
            }
        }

        binding.btnregister.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val phoneNumber = binding.phoneNumber.text.toString()
            val password = binding.password.text.toString()
            val passwordRe = binding.passwordRe.text.toString()

            if (password == passwordRe) {
                val user = User(
                    userId = null,
                    username = username,
                    email = email,
                    lat = null,
                    lng = null,
                    phone = phoneNumber,
                    role = selectedRole!!,
                    password = password
                )

                registerUser(user)
            } else {
                binding.passwordMissmatch.text = "비밀번호가 일치하지 않습니다."
            }
        }

    }

    private fun registerUser(user: User) {
        val call = RetrofitService.authService.registerUser(user)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    println("User registered successfully")
                } else {
                    println("Registration failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }
}