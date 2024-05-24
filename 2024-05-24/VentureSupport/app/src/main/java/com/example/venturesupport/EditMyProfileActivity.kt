package com.example.venturesupport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.EditmyprofileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMyProfileActivity: AppCompatActivity() {
    private val binding: EditmyprofileBinding by lazy {
        EditmyprofileBinding.inflate(layoutInflater)
    }
    private var myprofileLists = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.userEmailText.text = myprofileLists[0].email
        binding.userIdText.text = myprofileLists[0].user_id
        binding.userNameText.text = myprofileLists[0].user_name
        binding.userPhoneNumberText.text = myprofileLists[0].phone
        binding.userPasswordText.text = myprofileLists[0].password

        binding.editButton.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val id = binding.userId.text.toString()
            val name = binding.userName.text.toString()
            val phone_number = binding.userPhoneNumber.text.toString()
            val password = binding.userPassword.text.toString()
            val confirm_password = binding.userPassword2.text.toString()

            if (password == confirm_password) {
                updateUserInfo(email, id, name, phone_number, password)
                myprofileLists[0].email = email
                myprofileLists[0].user_id = id
                myprofileLists[0].user_name = name
                myprofileLists[0].phone = phone_number
                myprofileLists[0].password = password
                val intent = Intent(this, EditMyProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
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

    private fun updateUserInfo(email: String, id: String, name: String, phone_number: String, password: String) {
        val call = RetrofitService.userService.updateUser(email, id, name, phone_number, password)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    // 서버에서 업데이트된 사용자 정보를 받았을 때의 처리
                    val updatedUser = response.body()
                    if (updatedUser != null) {
                        // 업데이트된 사용자 정보를 처리
                    }
                } else {
                    // 서버 요청이 실패했을 때의 처리
                    Log.e("EditMyProfileActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // 네트워크 요청이 실패했을 때의 처리
                Log.e("EditMyProfileActivity", "Network request failed", t)
            }
        })
    }
}