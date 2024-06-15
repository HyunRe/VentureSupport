package com.example.test2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var intentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setBottomNavigationView()

        if (savedInstanceState == null) {
            binding.navigationView.selectedItemId = R.id.home
        }
    }

    fun setBottomNavigationView() {
        @Suppress("DEPRECATION")
        intentUser = intent.getParcelableExtra("intentUser")

        binding.navigationView.setOnItemSelectedListener { item ->
            val transaction = supportFragmentManager.beginTransaction()
            val bundle = Bundle()

            when (item.itemId) {
                R.id.home -> {
                    val homeFragment = HomeActivity()
                    bundle.putParcelable("intentUser", intentUser)
                    homeFragment.arguments = bundle
                    transaction.replace(R.id.fragmentContainer, homeFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.map -> {
                    val mapFragment = MapActivity()
                    bundle.putParcelable("intentUser", intentUser)
                    mapFragment.arguments = bundle
                    transaction.replace(R.id.fragmentContainer, mapFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.scheduler -> {
                    val schedulerFragment = SchedulerActivity()
                    bundle.putParcelable("intentUser", intentUser)
                    schedulerFragment.arguments = bundle
                    transaction.replace(R.id.fragmentContainer, schedulerFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.myPage -> {
                    val profileFragment = MyProfileActivity()
                    bundle.putParcelable("intentUser", intentUser)
                    profileFragment.arguments = bundle
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