package com.example.myactivity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.client.AuthCli
import com.example.myactivity.data.model.User
import com.example.myactivity.data.model.UserRole
import com.example.myactivity.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var authCli: AuthCli

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize AuthCli
        authCli = AuthCli(this)

        // Set click listener for sign button
        binding.sign.setOnClickListener {
            val user = createUserFromInput()
            authCli.registerUser(user)
        }

        // Toggle visibility of username based on the selected radio button
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val isVisible = checkedId == binding.retailUserBtn.id
            binding.username.visibility = if (isVisible) EditText.VISIBLE else EditText.INVISIBLE
        }
    }

    private fun createUserFromInput(): User {
        val username =
            if (binding.username.visibility == EditText.VISIBLE) binding.username.text.toString() else null
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val navId =
            if (binding.navId.visibility == EditText.VISIBLE) binding.navId.text.toString() else "NAV123"
        val phone = binding.phone.text.toString()
        val role = if (binding.wholeUserBtn.isChecked) UserRole.DRIVER else UserRole.SUPPLIER
        val lat = 10.1234 // Default value
        val lng = 20.5678 // Default value
        return User(0, username, password, email, phone, lat, lng, role)
    }
}
