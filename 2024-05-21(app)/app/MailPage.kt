//이메일 정보로 가입정보를 확인/복구시키는 화면
package com.example.myapp

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.feapp.databinding.ActivityMailPageBinding
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
import org.json.JSONObject
import java.io.IOException

class MailPage : AppCompatActivity() {

    private lateinit var binding: ActivityMailPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recoveryButton.setOnClickListener {
            handleRecovery()
        }

        binding.resetButton.setOnClickListener {
            handleMail()
        }
    }

    private fun handleRecovery() {
        val email = binding.emailEditText.text.toString()
        val userName = binding.userNameEditText.text.toString()

        if (email.isEmpty() || userName.isEmpty()) {
            showAlert("Error", "Please enter your email and user name")
            return
        }

        val url = "http://223.194.157.56:8080/login"
        val client = OkHttpClient()

        val json = JSONObject().apply {
            put("email", email)
            put("userName", userName)
        }

        /*val body = RequestBody.create(
            MediaType.get("application/json; charset=utf-8"),
            json.toString()
        )*/
        val body = json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
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
                            showAlert("Success", "Recovery successful: $responseData")
                        } else {
                            showAlert("Error", "Failed to recover: $responseData")
                        }
                    }
                }
            })
        }
    }

    private fun handleMail() {
        val userName = binding.userNameEditText.text.toString()

        if (userName.isEmpty()) {
            showAlert("Error", "Please enter your user name")
            return
        }

        val url = "http://223.194.157.56:8080/login"
        val client = OkHttpClient()

        val json = JSONObject().apply {
            put("userName", userName)
        }

        val body = json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
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
                            showAlert("Success", "Mail reset successful: $responseData")
                        } else {
                            showAlert("Error", "Failed to reset mail: $responseData")
                        }
                    }
                }
            })
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
