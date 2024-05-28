package com.example.app.ui.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.example.app.model.UserModel
import com.example.app.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {



    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Ánh xạ các thành phần từ layout XML
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        // Đặt sự kiện click cho nút Login
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Tạo đối tượng UserModel
            val user = UserModel(username, "", password)

            // Gửi yêu cầu đăng nhập thông qua Retrofit
            ApiClient.authService.loginUser(user).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()
                    } else {
                        // Xử lý khi đăng nhập thất bại
                        Toast.makeText(applicationContext, "Login failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // Xử lý khi có lỗi trong quá trình đăng nhập
                    Toast.makeText(applicationContext, "Login failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
