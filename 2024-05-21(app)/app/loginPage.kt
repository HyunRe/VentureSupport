package com.example.feapp
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.feapp.databinding.ActivityLoginPageBinding
import com.example.myapp.MailPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONObject

class loginPage: AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            handleLogin()
        }

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, MailPage::class.java))
        }

        binding.updateInfoButton.setOnClickListener {
            // Handle update info action
        }

        binding.registerButton.setOnClickListener {
            // Handle register action
        }

        binding.naverLoginButton.setOnClickListener {
            // Handle Naver login action
        }
    }

    private fun handleLogin() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val url = "http://223.194.157.56:8080/login"

        if (email.isEmpty() && password.isEmpty()) {
            showAlert("Error", "Please enter email or userId")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val client = OkHttpClient()
                val json = JSONObject().apply {
                    put("email", email)
                    put("password", password)
                }

                val body = json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            showAlert("Error", "Network request failed")
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body?.string()
                        runOnUiThread {
                            if (response.isSuccessful) {
                                // Handle success
                                showAlert("Success", "Login successful: $responseData")
                            } else {
                                showAlert("Error", "Failed to login: $responseData")
                            }
                        }
                    }
                })
            } catch (e: Exception) {
                runOnUiThread {
                    showAlert("Error", "An error occurred: ${e.message}")
                }
            }
        }
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}