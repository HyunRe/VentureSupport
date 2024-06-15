package com.example.venturesupport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.EditmyprofileBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMyProfileActivity: AppCompatActivity() {
    private val binding: EditmyprofileBinding by lazy {
        EditmyprofileBinding.inflate(layoutInflater)
    }
    private var intentUser: User? = null
    private lateinit var email: String
    private lateinit var name: String
    private lateinit var phoneNumber: String
    private lateinit var password: String
    private lateinit var confirmPassword: String

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentUser = intent.getParcelableExtra("intentUser")

        binding.userEmailText.text = intentUser?.email
        binding.userNameText.text = intentUser?.username
        binding.userPhoneNumberText.text = intentUser?.userPhoneNumber
        binding.userPasswordText.text = intentUser?.userPassword

        val id = intentUser?.userId ?: 1
        val latitude = intentUser?.lat ?: 0.0
        val longitude = intentUser?.lng ?: 0.0

        binding.editButton.setOnClickListener {
            email = if (binding.userEmail.text.isNullOrEmpty()) {
                intentUser?.email.toString()
            } else {
                binding.userEmail.text.toString()
            }

            name = if (binding.userName.text.isNullOrEmpty()) {
                intentUser?.username.toString()
            } else {
                binding.userName.text.toString()
            }

            phoneNumber = if (binding.userPhoneNumber.text.isNullOrEmpty()) {
                intentUser?.userPhoneNumber.toString()
            } else {
                binding.userPhoneNumber.text.toString()
            }

            password = if (binding.userPassword.text.isNullOrEmpty()) {
                intentUser?.userPassword.toString()
            } else {
                binding.userPassword.text.toString()
            }

            confirmPassword = if (binding.userPassword2.text.isNullOrEmpty()) {
                intentUser?.userPassword.toString()
            } else {
                binding.userPassword2.text.toString()
            }

            // 비밀번호 확인
            if (password == confirmPassword) {
                // 비밀번호가 일치하면 사용자 정보 갱신
                updateUserInfo(id, email, name, phoneNumber, password, latitude, longitude)
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            } else {
                // 비밀번호가 일치하지 않으면 오류 다이얼로그 표시
                showErrorDialog()
            }
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("오류") // 다이얼로그 제목 설정
            .setMessage("비밀번호가 일치하지 않습니다.") // 다이얼로그 메시지 설정
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss() // 확인 버튼 클릭 시 다이얼로그 닫기
            }
            .show() // 다이얼로그 표시
    }

    private fun updateUserInfo(userId: Int, email: String, name: String, phoneNumber: String, password: String, latitude: Double, longitude: Double) {
        // 새로운 사용자 객체 생성
        val user = User(
            userId = userId,
            username = name,
            email = email,
            lat = latitude,
            lng = longitude,
            userPhoneNumber = phoneNumber,
            userPassword = password
        )

        val call = RetrofitService.userService.updateUser(userId, user)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // 요청이 성공하면 로그에 응답 메시지 출력
                    val responseBody = response.body()?.string()
                    Log.d("EditMyProfileActivity", "Response: $responseBody")
                } else {
                    // 요청이 실패하면 로그에 실패 상태 코드 출력
                    Log.e("EditMyProfileActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 네트워크 요청이 실패하면 로그에 오류 메시지 출력
                Log.e("EditMyProfileActivity", "Network request failed", t)
            }
        })
    }
}
