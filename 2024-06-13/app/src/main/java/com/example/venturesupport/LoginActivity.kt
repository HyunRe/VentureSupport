package com.example.venturesupport

import User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.venturesupport.Company
import com.example.venturesupport.CreateOrderActivity
import com.example.venturesupport.HomeActivity
import com.example.venturesupport.LogintoResisterActivity
import com.example.venturesupport.MainActivity
import com.example.venturesupport.R
import com.example.venturesupport.RetrofitService
import com.example.venturesupport.RetrofitService.authService
import com.example.venturesupport.Supplier
import com.example.venturesupport.UserRole
import com.example.venturesupport.databinding.LoginBinding
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.oauth.view.NidOAuthLoginButton
import com.nhn.android.oauth.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val binding: LoginBinding by lazy {
        LoginBinding.inflate(layoutInflater)
    }
    private var selectedRole: UserRole? = null  // 선택된 사용자 역할
    private var userEmail: String = ""  // 사용자 이메일
    private var password: String = ""  // 사용자 비밀번호
    private var intentUser: User? = null  // 로그인한 사용자 정보
    private var intentCompany: Company? = null  // 로그인한 회사 정보

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 라디오 버튼 클릭 리스너 설정
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedRole = when (checkedId) {
                binding.radioButtonCompany.id -> UserRole.COMPANY
                binding.radioButtonDriver.id -> UserRole.DRIVER
                else -> null
            }

            selectedRole?.let {
                Log.d("LoginActivity", "Selected role: $it")
            }
        }

        // 로그인 버튼 클릭 리스너 설정
        binding.btnlogin.setOnClickListener {
            userEmail = binding.email.text.toString()
            password = binding.password.text.toString()

            // 사용자 또는 회사 객체 생성
            val user = User(
                userId = null,
                username = "",
                email = userEmail,
                lat = 0.0,
                lng = 0.0,
                phone = "",
                //role = UserRole.DRIVER,
                password = password
            )
            val company = Company(
                companyId = null,
                companyName = "",
                companyEmail = userEmail,
                companyPhoneNumber = "",
                companyPassword = password
            )

            val supplier = Supplier(
                supplierId = null,
                supplierName = "",
                supplierPhoneNumber = "",
                supplierLocation = ""
            )

            // 선택한 역할에 따라 로그인 메서드 호출
            if (selectedRole == UserRole.DRIVER) {
                fetchAllUsers(object : FetchAllUsersCallback {
                    override fun onFetchComplete() {
                        loginUser(user)
                    }
                })
            } else {
                fetchAllCompanies(object : FetchAllCompaniesCallback {
                    override fun onFetchComplete() {
                        loginCompany(company)
                    }
                })
            }
        }

        // 회원가입 버튼 클릭 리스너 설정
        binding.btnregister.setOnClickListener {
            val intent = Intent(this, LogintoResisterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Iamport and WebView for Naver login
        Iamport.create(application)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
        }

        val clientId = getString(R.string.OAUTH_CLIENT_ID)
        val clientSecret = getString(R.string.OAUTH_CLIENT_SECRET)
        val clientName = getString(R.string.OAUTH_CLIENT_NAME)

        NaverIdLoginSDK.initialize(this, clientId, clientSecret, clientName)
        val btn = findViewById<NidOAuthLoginButton>(R.id.buttonOAuthLoginImg)
        btn.setOAuthLogin(object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                Log.e("NaverLogin", "onError: $message")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.e("NaverLogin", "onFailure: $message")
            }

            override fun onSuccess() {
                Log.d("NaverLogin", "success")
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        })
    }

    /**
     * 사용자 로그인 메서드입니다.
     * @param user User - 로그인할 사용자 객체
     */
    private fun loginUser(user: User) {
        val call = authService.loginUser(user)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    println("Login successful")
                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, CreateOrderActivity::class.java)
                    intent.putExtra("intentUser", intentUser)
                    intent.putExtra("intentCompany", intentCompany)
                    startActivity(intent)
                    finish()
                } else {
                    println("Login failed: ${response.errorBody()?.string()}")
                    binding.passwordError.text = "이메일 또는 비밀번호를 다시 확인하세요."
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("Error: ${t.message}")
                binding.passwordError.text = "이메일 또는 비밀번호를 다시 확인하세요."
            }
        })
    }

    /**
     * 회사 로그인 메서드입니다.
     * @param company Company - 로그인할 회사 객체
     */
    private fun loginCompany(company: Company) {
        val call = authService.loginCompany(company)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    println("Login successful")
                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("intentUser", intentUser)
                    intent.putExtra("intentCompany", intentCompany)
                    startActivity(intent)
                    finish()
                } else {
                    println("Login failed: ${response.errorBody()?.string()}")
                    binding.passwordError.text = "이메일 또는 비밀번호를 다시 확인하세요."
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("Error: ${t.message}")
                binding.passwordError.text = "이메일 또는 비밀번호를 다시 확인하세요."
            }
        })
    }

    /**
     * 모든 사용자 정보를 가져오는 메서드입니다.
     * @param callback FetchAllUsersCallback - 콜백 인터페이스
     */
    private fun fetchAllUsers(callback: FetchAllUsersCallback) {
        val call = RetrofitService.userService.getAllUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userList = response.body()
                    if (userList != null) {
                        for (user in userList) {
                            if (user.email == userEmail) { // 이메일이 일치하는 사용자를 찾습니다.
                                saveUser(user)
                                break // 이메일이 일치하는 사용자를 찾았으면 반복문 종료
                            }
                        }
                        callback.onFetchComplete()
                    } else {
                        Log.e("MainActivity", "No user data found in response")
                    }
                } else {
                    Log.e("MainActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("MainActivity", "Network request failed", t)
            }
        })
    }

    /**
     * 모든 회사 정보를 가져오는 메서드입니다.
     * @param callback FetchAllCompaniesCallback - 콜백 인터페이스
     */
    private fun fetchAllCompanies(callback: FetchAllCompaniesCallback) {
        val call = RetrofitService.companyService.getAllCompanies()
        call.enqueue(object : Callback<List<Company>> {
            override fun onResponse(call: Call<List<Company>>, response: Response<List<Company>>) {
                if (response.isSuccessful) {
                    val companyList = response.body()
                    if (companyList != null) {
                        for (company in companyList) {
                            if (company.companyEmail == userEmail) { // 이메일이 일치하는 사용자를 찾습니다.
                                saveCompany(company)
                                break // 이메일이 일치하는 사용자를 찾았으면 반복문 종료
                            }
                        }
                        callback.onFetchComplete()
                    } else {
                        Log.e("MainActivity", "No company data found in response")
                    }
                } else {
                    Log.e("MainActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Company>>, t: Throwable) {
                Log.e("MainActivity", "Network request failed", t)

            }
        })
    }

    /**
     * 사용자 정보를 처리하는 콜백 인터페이스입니다.
     */
    interface FetchAllUsersCallback {
        fun onFetchComplete()
    }

    /**
     * 회사 정보를 처리하는 콜백 인터페이스입니다.
     */
    interface FetchAllCompaniesCallback {
        fun onFetchComplete()
    }

    /**
     * 사용자 정보를 저장합니다.
     * @param user User - 저장할 사용자 객체
     */
    private fun saveUser(user: User) {
        intentUser = user
    }

    /**
     * 회사 정보를 저장합니다.
     * @param company Company - 저장할 회사 객체
     */
    private fun saveCompany(company: Company) {
        intentCompany = company
    }
}
