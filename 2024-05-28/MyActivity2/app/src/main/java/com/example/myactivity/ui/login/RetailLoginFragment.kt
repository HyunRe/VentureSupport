package com.example.myactivity.ui.retail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myactivity.R
import com.example.myactivity.databinding.FragmentRetailLoginBinding

class RetailLoginFragment : Fragment() {
    // View Binding을 위한 변수 - Variable for View Binding
    private var _binding: FragmentRetailLoginBinding? = null
    private val binding get() = _binding!!

    // Fragment의 뷰를 생성하는 함수 - Function to create the Fragment's view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRetailLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 뷰가 생성된 후 호출되는 함수 - Function called after the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    // UI 초기 설정을 위한 함수 - Function to set up the UI
    private fun setupUI() {
        // View Binding을 사용하여 초기화 - Initialize using View Binding
        val sectionLabel: TextView = binding.sectionLabel
        val idEditText: EditText = binding.id
        val passwordEditText: EditText = binding.password
        val retailLoginButton: Button = binding.retailloginButton
        val retailNaverLoginButton: Button = binding.retailnaverLoginButton
        val wholesaleSignButton: Button = binding.wholesaleSignButton
        val wholesaleAccountButton: Button = binding.wholesaleAccountButton

        // 텍스트 설정 - Set text
        sectionLabel.text = getString(R.string.action_welcome)

        // 로그인 버튼 클릭 리스너 설정 - Set click listener for login button
        retailLoginButton.setOnClickListener {
            val id = idEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            if (id.isNotEmpty() && password.isNotEmpty()) {
                performLogin(id, password)
            } else {
                Toast.makeText(requireContext(), "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        // 네이버 로그인 버튼 클릭 리스너 설정 - Set click listener for Naver login button
        retailNaverLoginButton.setOnClickListener {
            // 네이버 로그인 로직 추가 - Add Naver login logic
            Toast.makeText(requireContext(), "네이버 로그인 기능은 현재 지원되지 않습니다", Toast.LENGTH_SHORT).show()
        }

        // 회원가입 버튼 클릭 리스너 설정 - Set click listener for sign up button
        wholesaleSignButton.setOnClickListener {
            // 회원가입 화면으로 네비게이트 - Navigate to sign up screen
            Toast.makeText(requireContext(), "회원가입 화면으로 이동", Toast.LENGTH_SHORT).show()
        }

        // 계정 찾기 버튼 클릭 리스너 설정 - Set click listener for account recovery button
        wholesaleAccountButton.setOnClickListener {
            // 계정 찾기 화면으로 네비게이트 - Navigate to account recovery screen
            Toast.makeText(requireContext(), "계정 찾기 화면으로 이동", Toast.LENGTH_SHORT).show()
        }
    }

    // 로그인 로직을 수행하는 함수 - Function to perform login logic
    private fun performLogin(id: String, password: String) {
        // 로그인 요청을 보내는 로직 추가 - Add logic to send login request
        Toast.makeText(requireContext(), "로그인 요청: $id", Toast.LENGTH_SHORT).show()
    }

    // Fragment의 뷰가 파괴될 때 호출되는 함수 - Function called when the Fragment's view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
