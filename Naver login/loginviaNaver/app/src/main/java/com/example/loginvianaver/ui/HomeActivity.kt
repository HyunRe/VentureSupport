package com.example.loginvianaver.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.loginvianaver.R
import com.example.loginvianaver.modell.UserRole
import com.example.loginvianaver.modell.Product
import com.example.loginvianaver.modell.User
import com.example.loginvianaver.service.ApiClient
import com.navercorp.nid.NaverIdLoginSDK
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class HomeActivity : AppCompatActivity() {

    lateinit var btn: Button
    lateinit var btnLogout : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val list = listOf<Product>()
        val user = User(id = null, username = "hiendzvcl1", email = "hiendz1@example.com",lat = 9999.9999,lng = 999999.99, phone = "666",role = UserRole.COMPANY , password = "666")

        val call = ApiClient.apiService.loginUser(user)
        call.enqueue(object : Callback<String> {
            override fun onResponse(p0: Call<String>, p1: retrofit2.Response<String>) {
                val message = p1.body() // Response message
                Log.d("hien", "success8: ${message}")
            }

            override fun onFailure(p0: Call<String>, p1: Throwable) {
                Log.d("hien", "success9: , $p1")
            }

        })

        //get user info from naver login
        val accessToken = NaverIdLoginSDK.getAccessToken()


        //get all user info
        val getallUser = ApiClient.apiService.getAllUsers()
        getallUser.enqueue(object : Callback<List<User>>{
            override fun onResponse(p0: Call<List<User>>, p1: retrofit2.Response<List<User>>) {
                if(p1.isSuccessful){
                    val userList = p1.body()
                    userList?.forEach { user ->
                        Log.d("MainActivity", "User: ${user.username}")
                    }
                }
            }

            override fun onFailure(p0: Call<List<User>>, p1: Throwable) {

            }

        })

        btn = findViewById(R.id.btnsenddata)
        btnLogout = findViewById(R.id.logout)

        btnLogout.setOnClickListener {
            NaverIdLoginSDK.logout()
        }
        btn.setOnClickListener {
            getUserInfo(accessToken){
                createnewuser(it!!)
            }
        }
    }

    private fun getUserInfo(accessToken: String?, callback: (User?) -> Unit) {
        val apiUrl = "https://openapi.naver.com/v1/nid/me"
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Request.Method.GET, apiUrl,
            Response.Listener { response ->

                try {
                    val jsonObject = JSONObject(response)
                    val responseObj = jsonObject.getJSONObject("response")

                    val id = responseObj.getString("id")
                    val email = responseObj.getString("email")
                    val phone = responseObj.getString("mobile")
                    val username = responseObj.getString("name")

                    val user = User(id = 2, email = email, phone = phone, username = username, role = UserRole.COMPANY, lat = 999.666, lng = 666.999, password = "666")
                    Log.d("NaverUserInfo", user.toString())

                    // Call the callback with the user object
                    callback(user)
                } catch (e: JSONException) {
                    Log.e("NaverUserInfoError", "Error parsing JSON: ${e.message}")
                    callback(null)
                }
            },
            Response.ErrorListener { error ->
                Log.e("NaverUserInfoError", "Error fetching user info: ${error.message}")
                callback(null)
            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $accessToken"
                return headers
            }
        }

        requestQueue.add(stringRequest)
    }


    private fun updateUser(id : Int,user: User) {
        val call = ApiClient.apiService.updateUser(id, user)
        call.enqueue(object : Callback<User> {
            override fun onResponse(p0: Call<User>, p1: retrofit2.Response<User>) {
                if(p1.isSuccessful){
                    val data = p1.body()
                    Log.d("datacallback", "onResponse: $data")
                }
            }

            override fun onFailure(p0: Call<User>, p1: Throwable) {
                Log.d("datacallback", "onResponse: ${p1.message}")
            }

        })
    }

    private fun createnewuser(user: User){
        println(user)
        val createUser = ApiClient.apiService.createUser(user)
        createUser.enqueue(object : Callback<User>{
            override fun onResponse(p0: Call<User>, p1: retrofit2.Response<User>) {
                println("$p1, $p0")
                if(p1.isSuccessful){
                    val data = p1.body()
                    Log.d("datacallback", "onResponse: $data")
                }
            }

            override fun onFailure(p0: Call<User>, p1: Throwable) {
                Log.d("datacallback", "onResponse: ${p1.message}")
            }

        })
    }
}