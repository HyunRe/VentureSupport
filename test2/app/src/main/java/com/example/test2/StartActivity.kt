package com.example.test2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.StartBinding

class StartActivity: AppCompatActivity() {
    private val binding: StartBinding by lazy {
        StartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

