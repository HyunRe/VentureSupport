package com.example.test2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setBottomNavigationView()

        if (savedInstanceState == null) {
            binding.navigationView.selectedItemId = R.id.home
        }
    }

    fun setBottomNavigationView() {
        binding.navigationView.setOnItemSelectedListener { item ->
            val transaction = supportFragmentManager.beginTransaction()

            when (item.itemId) {
                R.id.home -> {
                    val homeFragment = HomeActivity()
                    transaction.replace(R.id.fragmentContainer, homeFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.map -> {
                    val mapFragment = MapActivity()
                    transaction.replace(R.id.fragmentContainer, mapFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.scheduler -> {
                    val schedulerFragment = SchedulerActivity()
                    transaction.replace(R.id.fragmentContainer, schedulerFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.myPage -> {
                    val profileFragment = MyProfileActivity()
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