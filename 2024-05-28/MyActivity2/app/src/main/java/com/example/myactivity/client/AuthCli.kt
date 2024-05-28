package com.example.myactivity.client
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.myactivity.data.RetrofitClient
import com.example.myactivity.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthCli(private val context: Context) {

    private val apiService = RetrofitClient.apiService

    // 사용자 등록
    fun registerUser(user: User) {
        apiService.registerUser(user).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("AuthClient", "사용자 등록 성공: ${response.body()}")
                    Toast.makeText(context, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("AuthClient", "사용자 등록 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("AuthClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 사용자 로그인
    fun loginUser(user: User) {
        apiService.loginUser(user).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("AuthClient", "로그인 성공: ${response.body()}")
                    Toast.makeText(context, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("AuthClient", "로그인 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("AuthClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}