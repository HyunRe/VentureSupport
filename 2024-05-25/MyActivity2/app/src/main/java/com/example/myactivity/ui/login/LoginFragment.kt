//로그인 과정 실행(도매/소매 구분해 로그인/로그아웃/회원가입/가입정보 탐색 등 통신 담당ㅇ)

package com.example.myactivity.ui.login

import LoginFormState
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myactivity.R
import com.example.myactivity.databinding.FragmentLoginBinding
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



class LoginFragment : Fragment() {
    private var loginViewModel: LoginViewModel? = null
    private var binding: FragmentLoginBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider().get(LoginViewModel::class.java)
            //.get(LoginViewModel::class.java)
        val usernameEditText = binding!!.username
        val passwordEditText = binding!!.password
        val loginButton = binding!!.login
        val loadingProgressBar = binding!!.loading
        loginViewModel!!.loginFormState.observe(
            getViewLifecycleOwner(),
            Observer<LoginFormState?> { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.setEnabled(loginFormState.isDataValid)
                if (loginFormState.usernameError != null) {
                    usernameEditText.error = getString(loginFormState.usernameError!!)
                }
                if (loginFormState.passwordError != null) {
                    passwordEditText.error = getString(loginFormState.passwordError!!)
                }
            })
        loginViewModel!!.loginResult.observe(
            getViewLifecycleOwner(),
            Observer<LoginResult?> { loginResult ->
                if (loginResult == null) {
                    return@Observer
                }
                loadingProgressBar.visibility = View.GONE
                if (loginResult.error != null) {
                    showLoginFailed(loginResult.error)
                }
                if (loginResult.success != null) {
                    updateUiWithUser(loginResult.success)
                }
            })
        val afterTextChangedListener: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel!!.loginDataChanged(
                    usernameEditText.getText().toString(),
                    passwordEditText.getText().toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel!!.login(
                    usernameEditText.getText().toString(),
                    passwordEditText.getText().toString()
                )
            }
            false
        }
        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel!!.login(
                usernameEditText.getText().toString(),
                passwordEditText.getText().toString()
            )
        }
    }

    private fun ViewModelProvider(loginFragment: LoginFragment): Any {

    }

    private fun updateUiWithUser(model: LoggedInUserView?) {
        val welcome = getString(R.string.welcome) + model!!.displayName
        // TODO : initiate successful logged in experience
        if (context != null && requireContext().applicationContext != null) {
            Toast.makeText(requireContext().applicationContext, welcome, Toast.LENGTH_LONG).show()
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int?) {
        if (context != null && requireContext().applicationContext != null) {
            Toast.makeText(
                requireContext().applicationContext,
                errorString!!,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //* btnLogin.setOnClickListener {
            // 로그인 로직: 상부에 이미 완성?
      //  } 아래 코드 코틀린식으로 작성하디

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

    private fun runOnUiThread(function: () -> Unit) {

    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

