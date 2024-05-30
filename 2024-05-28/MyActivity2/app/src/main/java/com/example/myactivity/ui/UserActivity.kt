package com.example.myactivity.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.data.ApiClient
import com.example.myactivity.data.model.User
import com.example.myactivity.data.service.UserService
import com.example.myactivity.databinding.ActivityUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using the binding class
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userService = ApiClient.userService

        binding.btnGetAllUsers.setOnClickListener { getAllUsers() }
        binding.btnGetUser.setOnClickListener { getUserById() }
        binding.btnCreateUser.setOnClickListener { createUser() }
        binding.btnUpdateUser.setOnClickListener { updateUser() }
        binding.btnDeleteUser.setOnClickListener { deleteUser() }
    }

    private fun getAllUsers() {
        userService.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    binding.tvUserInfo.text = users.toString()
                    Toast.makeText(this@UserActivity, "Fetched all users", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@UserActivity, "Failed to fetch users", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@UserActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUserById() {
        val userId = binding.etUserId.text.toString().toIntOrNull()
        if (userId == null) {
            Toast.makeText(this, "Please enter a valid user ID", Toast.LENGTH_SHORT).show()
            return
        }

        userService.getUserById(userId).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    binding.tvUserInfo.text = user.toString()
                    Toast.makeText(this@UserActivity, "Fetched user by ID", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@UserActivity, "Failed to fetch user", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@UserActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createUser() {
        val userName = binding.etUserName.text.toString()
        val userEmail = binding.etUserEmail.text.toString()

        if (userName.isEmpty() || userEmail.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val user = User(username = userName, email = userEmail)
        userService.createUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    binding.tvUserInfo.text = "User created: ${response.body()}"
                    Toast.makeText(this@UserActivity, "User created", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@UserActivity, "Failed to create user", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@UserActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUser() {
        val userId = binding.etUserId.text.toString().toIntOrNull()
        val userName = binding.etUserName.text.toString()
        val userEmail = binding.etUserEmail.text.toString()

        if (userId == null || userName.isEmpty() || userEmail.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val user = User(userId, userName, userEmail)
        userService.updateUser(userId, user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    binding.tvUserInfo.text = "User updated: ${response.body()}"
                    Toast.makeText(this@UserActivity, "User updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@UserActivity, "Failed to update user", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@UserActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteUser() {
        val userId = binding.etUserId.text.toString().toIntOrNull()
        if (userId == null) {
            Toast.makeText(this, "Please enter a valid user ID", Toast.LENGTH_SHORT).show()
            return
        }

        userService.deleteUser(userId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    binding.tvUserInfo.text = "User deleted"
                    Toast.makeText(this@UserActivity, "User deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@UserActivity, "Failed to delete user", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@UserActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
