package com.example.myactivity.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myactivity.R
import com.example.myactivity.databinding.FragmentHomeBinding

// HomeFragment는 초기 화면으로, 원장관리(소매업자용)과 로그인(도매업자용)을 선택할 수 있는 화면을 구현합니다.
class HomeFragment : Fragment() {

    // 뷰 바인딩 객체. onCreateView와 onDestroyView 사이에서만 유효합니다.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // 프래그먼트가 처음 생성될 때 호출되는 함수
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ViewModel 초기화
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // 뷰 바인딩 초기화
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 뷰가 생성된 후 호출되는 함수
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 텍스트 뷰 초기화
        val textView: TextView = binding.textHome

        // 버튼 클릭 리스너 설정
        // 도매 로그인 버튼
        view.findViewById<Button>(R.id.btn_wholesale).setOnClickListener {
            Log.d("HomeFragment", "Wholesale login button clicked")
            findNavController().navigate(R.id.action_home_to_wholesale_login)
        }

        // 소매 로그인 버튼
        view.findViewById<Button>(R.id.btn_retail).setOnClickListener {
            Log.d("HomeFragment", "Retail login button clicked")
            findNavController().navigate(R.id.action_home_to_retail_login)
        }

        // 회원가입 버튼
        view.findViewById<Button>(R.id.btn_sign).setOnClickListener {
            Log.d("HomeFragment", "Sign up button clicked")
            findNavController().navigate(R.id.action_home_to_sign)
        }
    }

    // 뷰가 파괴될 때 호출되는 함수
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 뷰 바인딩 해제
    }
}
