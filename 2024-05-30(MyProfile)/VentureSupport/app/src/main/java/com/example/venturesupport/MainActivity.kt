package com.example.venturesupport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.venturesupport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // onCreate: 액티비티가 생성될 때 호출되는 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // 뷰 설정

        // 버튼 클릭 리스너 설정
        binding.button.setOnClickListener {
            // MyProfileActivity 프래그먼트를 로드
            loadFragment(MyProfileActivity())
        }
    }

    // loadFragment: 주어진 프래그먼트를 로드하는 함수
    private fun loadFragment(fragment: Fragment) {
        // FragmentManager 생성
        val fragmentManager = supportFragmentManager

        // FragmentTransaction 생성하여 트랜잭션 시작 및 프래그먼트 교체
        val fragmentTransaction = fragmentManager.beginTransaction()

        // FrameLayout을 새로운 프래그먼트로 교체
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        // 트랜잭션을 백스택에 추가
        fragmentTransaction.addToBackStack(null)
        // 트랜잭션 커밋
        fragmentTransaction.commit()
    }
}
