package com.example.logapp2.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.logapp2.R
import com.example.logapp2.data.User
import com.example.logapp2.data.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory())
                .get(LoginViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_login, container, false)

        usernameEditText = root.findViewById(R.id.email)
        passwordEditText = root.findViewById(R.id.password)
        loginButton = root.findViewById(R.id.login)
        loadingProgressBar = root.findViewById(R.id.loading)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Retrofit 서비스를 호출하여 로그인 요청
            loginUser(User(username, password))
        }

        return root
    }

    private fun loginUser(user: User) {
        loadingProgressBar.visibility = View.VISIBLE

        // Retrofit을 사용하여 서버와 통신하는 코드 호출
        val call = RetrofitInstance.userService.loginUser(user)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                loadingProgressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    // 로그인 성공
                    val message = response.body()
                    println("로그인 성공: $message")
                } else {
                    // 서버 에러
                    println("서버 에러: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                loadingProgressBar.visibility = View.GONE
                // 네트워크 에러
                println("네트워크 에러: ${t.message}")
            }
        })
    }
}
