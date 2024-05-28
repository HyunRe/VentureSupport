package com.example.logapp2.ui.login
package com.example.myactivity.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myactivity.R
import com.example.myactivity.data.model.ApiResponse
import com.example.myactivity.data.model.User
import com.example.myactivity.data.network.RetrofitClient
import com.example.myactivity.databinding.FragmentSignBinding
import kotlinx.coroutines.launch
import retrofit2.Response

class SignFragment : Fragment() {
    private var _binding: FragmentSignBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val emailEditText = binding.email
        val latitudeEditText = binding.latitude
        val longitudeEditText = binding.longitude
        val navIdEditText = binding.navId
        val signButton = binding.sign
        val loadingProgressBar = binding.loading
        val errorMessage = binding.errorMessage

        val afterTextChangedListener: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                // Enable the sign button only if required fields are filled
                signButton.isEnabled = passwordEditText.text.isNotEmpty() &&
                        (usernameEditText.text.isNotEmpty() || emailEditText.text.isNotEmpty())
            }
        }

        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        emailEditText.addTextChangedListener(afterTextChangedListener)

        passwordEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                attemptSignUp()
            }
            false
        }

        signButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            attemptSignUp()
        }
    }

    private fun attemptSignUp() {
        val username = binding.username.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val latitude = binding.latitude.text.toString().trim()
        val longitude = binding.longitude.text.toString().trim()
        val navId = binding.navId.text.toString().trim()

        if (password.isEmpty() || (email.isEmpty() && username.isEmpty())) {
            binding.errorMessage.visibility = View.VISIBLE
        } else {
            binding.errorMessage.visibility = View.GONE
            binding.loading.visibility = View.VISIBLE

            val user = User(
                username = if (username.isEmpty()) null else username,
                password = if (password.isEmpty()) null else password,
                email = if (email.isEmpty()) null else email,
                latitude = if (latitude.isEmpty()) 0.0 else latitude.toDouble(),
                longitude = if (longitude.isEmpty()) 0.0 else longitude.toDouble(),
                navId = if (navId.isEmpty()) null else navId
            )

            // Retrofit 요청을 위한 Coroutine
            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.registerUser(user)
                    binding.loading.visibility = View.GONE
                    handleResponse(response)
                } catch (e: Exception) {
                    binding.loading.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = "서버 요청 실패: ${e.message}"
                }
            }
        }
    }

    private fun handleResponse(response: Response<ApiResponse>) {
        if (response.isSuccessful) {
            val apiResponse = response.body()
            if (apiResponse != null && apiResponse.success) {
                if (apiResponse.userType == "retail") {
                    findNavController().navigate(R.id.action_sign_to_retail_login)
                } else if (apiResponse.userType == "wholesale") {
                    findNavController().navigate(R.id.action_sign_to_wholesale_login)
                }
            } else {
                binding.errorMessage.visibility = View.VISIBLE
                binding.errorMessage.text = "등록 실패: ${apiResponse?.message}"
            }
        } else {
            binding.errorMessage.visibility = View.VISIBLE
            binding.errorMessage.text = "등록 실패: ${response.message()}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}