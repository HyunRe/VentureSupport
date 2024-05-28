package com.example.myactivity.ui
import com.example.myactivity.client.AuthCli
import com.example.myactivity.data.model.User
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private var authClient = AuthCli()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authClient = AuthCli()

        // 사용자 등록 예제
        binding.buttonRegister.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            val email = binding.editTextEmail.text.toString()
            val user = User(username, password, email)
            authClient.registerUser(user)
        }

        // 사용자 로그인 예제
        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            val user = User(username, password)
            authClient.loginUser(user)
        }
    }
}

