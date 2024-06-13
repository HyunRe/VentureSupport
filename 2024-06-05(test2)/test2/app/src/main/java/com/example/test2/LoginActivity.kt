package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.RetrofitService.authService
import com.example.test2.databinding.LoginBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 로그인 화면 Activity입니다.
 * 사용자가 로그인 정보를 입력하고, 선택한 역할에 따라 로그인 기능을 수행합니다.
 */
class LoginActivity: AppCompatActivity() {
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
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
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
                username = null,
                email = userEmail,
                userPhoneNumber = null,
                lat = null,
                lng = null,
                userPassword = password,
                inventoryQuantity = null
            )
            val company = Company (
                companyId = null,
                companyName = "",
                companyEmail = userEmail,
                companyPhoneNumber = "",
                companyPassword = password
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
