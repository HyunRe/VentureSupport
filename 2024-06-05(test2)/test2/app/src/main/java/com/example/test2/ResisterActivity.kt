package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        // 라디오 버튼 선택 리스너 설정: 사용자가 역할을 선택할 수 있도록 설정합니다.
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            selectedRole = when (checkedId) {
                binding.radioButtonCompany.id -> UserRole.COMPANY
                binding.radioButtonDriver.id -> UserRole.DRIVER
                else -> null
            }
            // 선택된 역할을 로그에 기록
            selectedRole?.let {
                Log.d("LoginActivity", "Selected role: $it")
            }
        }
// 회원 가입 버튼 클릭 리스너 설정
        binding.btnregister.setOnClickListener {
            // 사용자 입력값 가져오기
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val phoneNumber = binding.phoneNumber.text.toString()
            val password = binding.password.text.toString()
            val passwordRe = binding.passwordRe.text.toString()

            // 비밀번호 확인 필드와 비밀번호가 일치하는지 확인
            if (password == passwordRe) {
                // 사용자 또는 회사 객체 생성
                val user = User(
                    userId = null,
                    username = username,
                    email = email,
                    userPhoneNumber = phoneNumber,
                    lat = null,
                    lng = null,
                    userPassword = password,
                    inventoryQuantity = null
                )
                val company = Company (
                    companyId = null,
                    companyName = username,
                    companyEmail = email,
                    companyPhoneNumber = phoneNumber,
                    companyPassword = password
                )
                // 선택된 역할에 따라 회원 가입 처리
                if (selectedRole == UserRole.DRIVER) {
                    registerUser(user)
                } else {
                    registerCompany(company)
                }

            } else {
                // 비밀번호 불일치 메시지 표시
                binding.passwordMissmatch.text = "비밀번호가 일치하지 않습니다."
            }
        }

    }
    // 사용자 회원 가입 처리 함수
    private fun registerUser(user: User) {
        val call = RetrofitService.authService.registerUser(user)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                // 회원 가입 성공 시 토스트 메시지와 로그인 화면으로 이동
                Toast.makeText(this@ResisterActivity, "회원 가입 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ResisterActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                // 회원 가입 실패 시 에러 로그 출력
                println("Registration failed: ${response.errorBody()?.string()}")
            }
        }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }

    // 회사 회원 가입 처리 함수
    private fun registerCompany(company: Company) {
        val call = RetrofitService.authService.registerCompany(company)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    println("User registered successfully")
                    // 회원 가입 성공 시 토스트 메시지와 로그인 화면으로 이동
                    Toast.makeText(this@ResisterActivity, "회원 가입 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ResisterActivity, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
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