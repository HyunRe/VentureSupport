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

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Ánh xạ các thành phần từ layout XML
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)

        // Đặt sự kiện click cho nút Register
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Tạo đối tượng UserModel
            val user = UserModel(username, email, password)

            // Gửi yêu cầu đăng ký thông qua Retrofit
            ApiClient.authService.registerUser(user).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Toast.xhmfmakeText(applicationContext, "Registration successful", Toast.LENGTH_SHORT).show()
                        // Đăng ký thành công, có thể chuyển người dùng đến màn hình đăng nhập hoặc màn hình chính của ứng dụng
                    } else {
                        // Xử lý khi đăng ký thất bại
                        Toast.makeText(applicationContext, "Registration failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // Xử lý khi có lỗi trong quá trình đăng ký
                    Toast.makeText(applicationContext, "Registration failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
