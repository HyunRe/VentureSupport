package com.example.myactivity.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myactivity.R
import com.example.myactivity.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using the binding class
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up BottomNavigationView with NavController
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.mobile_navigation)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.mobile_navigation)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
