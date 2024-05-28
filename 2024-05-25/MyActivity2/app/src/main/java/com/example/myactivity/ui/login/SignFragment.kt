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
    // View Binding을 위한 변수 - Variable for View Binding
    private var _binding: FragmentSignBinding? = null
    private val binding get() = _binding!!

    // Fragment의 뷰를 생성하는 함수 - Function to create the Fragment's view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 뷰가 생성된 후 호출되는 함수 - Function called after the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    // UI 초기 설정을 위한 함수 - Function to set up the UI
    private fun setupUI() {
        // EditText와 버튼을 ViewBinding을 사용하여 초기화 - Initialize EditTexts and buttons using ViewBinding
        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val emailEditText = binding.email
        val navIdEditText = binding.navId
        val signButton = binding.sign
        val loadingProgressBar = binding.loading
        val errorMessage = binding.errorMessage

        // 텍스트 변경 리스너 - Text change listener
        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                // 조건에 따라 가입 버튼 활성화 - Enable sign button based on conditions
                signButton.isEnabled = passwordEditText.text.isNotEmpty() &&
                        (usernameEditText.text.isNotEmpty() || emailEditText.text.isNotEmpty())
            }
        }

        // EditText에 텍스트 변경 리스너 추가 - Adding text change listener to EditTexts
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        emailEditText.addTextChangedListener(afterTextChangedListener)

        // 패스워드 입력 후 완료 버튼 눌렀을 때의 동작 정의 - Define action when done button is pressed after entering password
        passwordEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                attemptSignUp() // 회원가입 시도 - Attempt to sign up
            }
            false
        }

        // 가입 버튼 클릭 리스너 설정 - Set click listener for sign up button
        signButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE // 로딩 표시 - Show loading indicator
            attemptSignUp() // 회원가입 시도 - Attempt to sign up
        }
    }

    // 회원가입 시도 함수 - Function to attempt sign up
    private fun attemptSignUp() {
        // 입력된 값들을 가져오기 - Get entered values
        val username = binding.username.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val navId = binding.navId.text.toString().trim()

        // 필수 필드 확인 - Check mandatory fields
        if (password.isEmpty() || (email.isEmpty() && username.isEmpty())) {
            binding.errorMessage.visibility = View.VISIBLE // 에러 메시지 표시 - Show error message
        } else {
            binding.errorMessage.visibility = View.GONE
            binding.loading.visibility = View.VISIBLE

            // User 객체 생성 - Create User object
            val user = User(
                username = if (username.isEmpty()) null else username,
                password = if (password.isEmpty()) null else password,
                email = if (email.isEmpty()) null else email,
                longitude = null, // Null로 설정 - Set to null
                navId = if (navId.isEmpty()) null else navId
            )

            // Retrofit 요청을 위한 Coroutine 사용 - Use Coroutine for Retrofit request
            lifecycleScope.launch {
                try {
                    // 서버로 회원가입 요청 - Request registration to server
                    val response = RetrofitClient.apiService.registerUser(user)
                    binding.loading.visibility = View.GONE
                    handleResponse(response) // 응답 처리 - Handle response
                } catch (e: Exception) {
                    // 예외 처리 - Handle exception
                    binding.loading.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = "서버 요청 실패: ${e.message}"
                }
            }
        }
    }

    // 서버 응답 처리 함수 - Function to handle server response
    private fun handleResponse(response: Response<ApiResponse>) {
        if (response.isSuccessful) {
            val apiResponse = response.body()
            if (apiResponse != null && apiResponse.success) {
                // 사용자 유형에 따른 네비게이션 - Navigate based on user type
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

    // Fragment의 뷰가 파괴될 때 호출되는 함수 - Function called when the Fragment's view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
