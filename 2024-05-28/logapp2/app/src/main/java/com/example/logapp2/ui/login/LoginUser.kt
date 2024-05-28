package com.example.logapp2.ui.login
import com.example.logapp2.data.network.RetrofitInstance
import com.example.logapp2.data.User

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun loginUser(user: User) {
    val call = RetrofitInstance.userService.loginUser(user)

    call.enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
                // 로그인 성공
                val message = response.body()
                println("로그인 성공: $message")
            } else {
                // 서버 에러
                println("서버 에러: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            // 네트워크 에러
            println("네트워크 에러: ${t.message}")
        }
    })
}
