package com.example.venturesupport

import User
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

// EditMyProfileActivity 클래스: 사용자가 자신의 프로필을 수정할 수 있는 화면을 제공하는 액티비티
class EditMyProfileActivity: AppCompatActivity() {

    private val binding: EditmyprofileBinding by lazy {
        EditmyprofileBinding.inflate(layoutInflater)
    }

    // 사용자 정보를 저장할 변수
    private var user: User? = null

    // 사용자 입력 값을 저장할 변수
    private lateinit var email: String
    private lateinit var name: String
    private lateinit var phoneNumber: String
    private lateinit var password: String
    private lateinit var confirmPassword: String //확인용 비번

    // onCreate 함수: 액티비티 생성 시 호출되며, 초기 설정을 담당
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // 액티비티의 뷰를 설정

        // 사용자 정보를 인텐트에서 가져옴 (이전 API 사용으로 경고 억제)
        @Suppress("DEPRECATION")
        user = intent.getParcelableExtra("user")

        // 바인딩된 UI 요소에 사용자 정보 설정
        binding.userEmailText.setText(user?.email, TextView.BufferType.EDITABLE)
        binding.userNameText.setText(user?.username, TextView.BufferType.EDITABLE)
        binding.userPhoneNumberText.setText(user?.phone, TextView.BufferType.EDITABLE)
        binding.userPasswordText.setText(user?.password, TextView.BufferType.EDITABLE)

        // 사용자 고유 ID와 위치 정보, 역할 정보를 변수에 저장
        val id = user?.userId ?: 0 //일련번호(고유 아이디)
        val latitude = user?.lat ?: 0.0 //위도(기본값)
        val longitude = user?.lng ?: 0.0 //경도(기본값)
        //val role = user?.role ?: UserRole.COMPANY //업종구분(기본값)

        // 수정 버튼 클릭 리스너 설정
        binding.editButton.setOnClickListener {
            // 사용자가 입력한 정보를 변수에 저장, 비어있는 경우 기존 사용자 정보 사용
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

            // 비밀번호 확인
            if (password == confirmPassword) {
                // 비밀번호가 일치하면 사용자 정보 갱신
                updateUserInfo(id, email, name, phoneNumber, password, latitude, longitude)
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish() // 현재 액티비티 종료
            } else {
                // 비밀번호가 일치하지 않으면 오류 다이얼로그 표시
                showErrorDialog()
            }
        }
    }

    // showErrorDialog 함수: 비밀번호가 일치하지 않을 때 오류 다이얼로그를 표시
    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("오류") // 다이얼로그 제목 설정
            .setMessage("비밀번호가 일치하지 않습니다.") // 다이얼로그 메시지 설정
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss() // 확인 버튼 클릭 시 다이얼로그 닫기
            }
            .show() // 다이얼로그 표시
    }

    // updateUserInfo 함수: 사용자 정보를 갱신하는 네트워크 요청을 보냄
    private fun updateUserInfo(userId: Int, email: String, name: String, phoneNumber: String, password: String, latitude: Double, longitude: Double, role: UserRole) {
        // 새로운 사용자 객체 생성
        val user = User(
            userId = userId,
            username = name,
            email = email,
            lat = latitude,
            lng = longitude,
            phone = phoneNumber,
            password = password
        )

        // Retrofit을 이용한 네트워크 요청
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
