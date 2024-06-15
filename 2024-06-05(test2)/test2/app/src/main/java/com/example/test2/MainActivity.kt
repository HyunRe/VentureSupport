package com.example.test2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test2.databinding.ActivityMainBinding
//import com.example.venturesupport.MapActivity

/**
 * 앱의 메인 화면을 구성하는 액티비티입니다.
 */
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var intentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 바텀 내비게이션 뷰 설정
        setBottomNavigationView()

        // 액티비티 생성 시 홈 화면을 기본으로 표시
        if (savedInstanceState == null) {
            binding.navigationView.selectedItemId = R.id.home
        }
    }

    /**
     * 바텀 내비게이션 뷰의 선택 리스너를 설정합니다.
     */
    fun setBottomNavigationView() {
        // 인텐트를 통해 사용자 정보를 받아옵니다.
        @Suppress("DEPRECATION")
        intentUser = intent.getParcelableExtra("intentUser")

        binding.navigationView.setOnItemSelectedListener { item ->
            val transaction = supportFragmentManager.beginTransaction()
            val bundle = Bundle()

            // 선택된 메뉴에 따라 해당 Fragment로 전환합니다.
            when (item.itemId) {
                R.id.home -> {
                    val homeFragment = HomeActivity()
                    bundle.putParcelable("intentUser", intentUser)
                    transaction.replace(R.id.fragmentContainer, homeFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.map -> {
                    val mapFragment = MapActivity<Any>()
                    bundle.putParcelable("intentUser", intentUser)
                    transaction.replace(R.id.fragmentContainer, mapFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.scheduler -> {
                    val schedulerFragment = SchedulerActivity()
                    bundle.putParcelable("intentUser", intentUser)
                    transaction.replace(R.id.fragmentContainer, schedulerFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.myPage -> {
                    val profileFragment = MyProfileActivity()
                    bundle.putParcelable("intentUser", intentUser)
                    transaction.replace(R.id.fragmentContainer, profileFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
