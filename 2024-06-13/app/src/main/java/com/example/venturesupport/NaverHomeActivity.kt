package com.example.venturesupport

import User
import android.app.usage.UsageEvents
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.metrics.Event
import android.os.Build
import android.os.Bundle
import android.util.EventLog
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.venturesupport.R
import com.example.venturesupport.MerchantReceiver
import com.example.venturesupport.PaymentResultData.result66
import com.example.venturesupport.ViewModel
import com.google.gson.GsonBuilder
import com.iamport.sdk.data.sdk.IamPortCertification
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.IamPortResponse
import com.iamport.sdk.data.sdk.PG
import com.iamport.sdk.data.sdk.PayMethod
import com.iamport.sdk.domain.core.ICallbackPaymentResult
import com.iamport.sdk.domain.core.Iamport
import com.iamport.sdk.domain.utils.CONST
import com.iamport.sdk.domain.utils.Event
import com.iamport.sdk.domain.utils.EventObserver
import com.iamport.sdk.domain.utils.Util
import com.navercorp.nid.NaverIdLoginSDK
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.util.Date
import com.iamport.sdk.data.sdk.IamPortCertification
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.IamPortResponse
import com.iamport.sdk.domain.core.ICallbackPaymentResult
import com.iamport.sdk.domain.core.Iamport
import com.iamport.sdk.domain.utils.Event // 올바른 Event 클래스 임포트
import com.iamport.sdk.domain.utils.EventObserver
import com.iamport.sdk.domain.utils.Util


class NaverHomeActivity : AppCompatActivity() {

    lateinit var btn: Button
    lateinit var btnLogout: Button
    lateinit var paymentButton: Button
    lateinit var payIpayButton: Button
    lateinit var certificationBtn: Button
    lateinit var spinner: Spinner
    lateinit var pgSpinner: Spinner
    lateinit var pgMethod: Spinner
    lateinit var name: EditText
    lateinit var amount: EditText
    lateinit var cardDirectCode: EditText

    // ViewModel 인스턴스
    private val viewModel: ViewModel by viewModels()

    // 포그라운드 서비스 리시버
    private val receiver = MerchantReceiver()

    // 액세스 토큰
    lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_naver_home)

        // 시스템 바 영역 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 초기 설정 및 액션 초기화
        initAction()
        Iamport.init(this)

        // 사용자 로그인 및 정보 가져오기
        loginUser()
        getAllUserInfo()

        // 포그라운드 서비스 리시버 등록
        registerForegroundServiceReceiver(this)
    }

    private fun initAction() {
        // 뷰 요소 초기화
        btn = findViewById(R.id.btnsenddata) // 사용자 정보 전송 버튼
        paymentButton = findViewById(R.id.paybutton) // 결제 버튼
        btnLogout = findViewById(R.id.logout) // 로그아웃 버튼
        payIpayButton = findViewById(R.id.payment_button) // 결제 아이페이 버튼
        certificationBtn = findViewById(R.id.certification_button) // 인증 버튼
        spinner = findViewById(R.id.user_code) // 사용자 코드 스피너
        pgSpinner = findViewById(R.id.pg) // PG 스피너
        pgMethod = findViewById(R.id.pg_method) // 결제 방법 스피너
        name = findViewById(R.id.name) // 이름 입력 필드
        amount = findViewById(R.id.amount) // 금액 입력 필드
        cardDirectCode = findViewById(R.id.card_direct_code) // 카드 직결 코드 입력 필드

        // 결제 버튼 클릭 리스너
        payIpayButton.setOnClickListener {
            onClickPayment()
        }

        // 인증 버튼 클릭 리스너
        certificationBtn.setOnClickListener {
            onClickCertification()
        }

        // 사용자 코드 스피너 어댑터 설정
        val userCodeAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item,
            Util.getUserCodeList() // 사용자 코드 리스트 가져오기
        )

        // PG 스피너 어댑터 설정
        val pgAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item,
            PG.getPGNames() // PG 이름 리스트 가져오기
        )

        spinner.adapter = userCodeAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.userCode = Util.getUserCode(spinner.selectedItemPosition) // 선택된 사용자 코드 설정
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        pgSpinner.adapter = pgAdapter
        pgSpinner.onItemSelectedListener = pgSelectListener

        // 이름 입력 필드 텍스트 변경 리스너
        name.doAfterTextChanged {
            viewModel.paymentName = it.toString() // 결제 이름 설정
        }
        name.setText("test payment") // 기본값 설정

        // 금액 입력 필드 텍스트 변경 리스너
        amount.doAfterTextChanged {
            viewModel.amount = it.toString() // 결제 금액 설정
        }
        amount.setText("1000") // 기본값 설정

        // 카드 직결 코드 입력 필드 텍스트 변경 리스너
        cardDirectCode.doAfterTextChanged {
            viewModel.cardDirectCode = it.toString() // 카드 직결 코드 설정
        }

        // 로그아웃 버튼 클릭 리스너
        btnLogout.setOnClickListener {
            NaverIdLoginSDK.logout() // 네이버 로그아웃
        }

        // 사용자 정보 가져오기 버튼 클릭 리스너
        btn.setOnClickListener {
            getUserInfo(accessToken) {
                createNewUser(it!!) // 새로운 사용자 생성
            }
        }
    }

    override fun onStart() {
        // 결제 결과 콜백 리스너 설정
        viewModel.resultCallback.observe(this, EventObserver {
            startActivity(Intent(this@NaverHomeActivity, PaymentResultActivity::class.java))
        })
        super.onStart()
    }

    private fun onClickPayment() {
        val userCode = viewModel.userCode
        val request = viewModel.createIamPortRequest() // 결제 요청 생성

        Iamport.payment(userCode, iamPortRequest = request) { callBackListener.result(it) }
    }

    private fun onClickCertification() {
        val userCode = "iamport"
        val certification = IamPortCertification(
            merchant_uid = getRandomMerchantUid(), // 랜덤한 머천트 UID 생성
            company = "유어포트", // 회사명 설정
        )

        Iamport.certification(userCode, iamPortCertification = certification) { callBackListener.result(it) }
    }

    private fun registerForegroundServiceReceiver(context: Context) {
        // 포그라운드 서비스 리시버 등록
        Iamport.enableChaiPollingForegroundService(enableService = true, enableFailStopButton = true)

        val intentFilter = IntentFilter().apply {
            addAction(CONST.BROADCAST_FOREGROUND_SERVICE)
            addAction(CONST.BROADCAST_FOREGROUND_SERVICE_STOP)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(receiver, intentFilter, Context.RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(receiver, intentFilter)
        }
    }


    private val callBackListener = object : ICallbackPaymentResult {
        override fun result(iamPortResponse: IamPortResponse?) {
            val resJson = GsonBuilder().setPrettyPrinting().create().toJson(iamPortResponse)
            Log.i("SAMPLE", "결제 결과 콜백\n$resJson")
            result66 = iamPortResponse
            if (iamPortResponse != null) {
                startActivity(Intent(this@NaverHomeActivity, PaymentResultActivity::class.java))
                viewModel.resultCallback.postValue(Event(iamPortResponse))
            }
        }
    }

    private val pgSelectListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.pg = PG.values()[position] // 선택된 PG 설정
            pgMethod.adapter = ArrayAdapter(
                this@NaverHomeActivity, android.R.layout.simple_spinner_dropdown_item,
                Util.convertPayMethodNames(PG.values()[position]) // 결제 방법 리스트 설정
            )

            pgMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    viewModel.payMethod = Util.getMappingPayMethod(viewModel.pg).elementAt(pgMethod.selectedItemPosition) // 선택된 결제 방법 설정
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private fun getUserInfo(accessToken: String?, callback: (User?) -> Unit) {
        val apiUrl = "https://openapi.naver.com/v1/nid/me"
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Request.Method.GET, apiUrl,
            com.android.volley.Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val responseObj = jsonObject.getJSONObject("response")

                    val id = responseObj.getString("id")
                    val email = responseObj.getString("email")
                    val phone = responseObj.getString("mobile")
                    val username = responseObj.getString("name")

                    // User 객체 생성
                    val user = User(
                        id = 2, // 예시 사용자 ID
                        email = email,
                        phone = phone,
                        username = username,
                        lat = 999.666,
                        lng = 666.999,
                        password = "666",
                    )
                    Log.d("NaverUserInfo", id)

                    // 콜백 함수 호출
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
                return mapOf("Authorization" to "Bearer $accessToken")
            }
        }

        requestQueue.add(stringRequest)
    }

    private fun updateUser(id: Int, user: User) {
        val call = RetrofitService.userService.updateUser(id, user)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("datacallback", "onResponse: $data")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("datacallback", "onResponse: ${t.message}")
            }
        })
    }

    private fun createNewUser(user: User) {
        val createUser = RetrofitService.userService.createUser(user)
        createUser.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("datacallback", "onResponse: $data")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("datacallback", "onResponse: ${t.message}")
            }
        })
    }

    private fun loginUser() {
        val user = User(
            id = null,
            username = "hiendzvcl1", // 예시 사용자 이름
            email = "hiendz1@example.com", // 예시 이메일
            lat = 9999.9999,
            lng = 999999.99,
            phone = "666",
            password = "666"
        )

        val call = RetrofitService.authService.loginUser(user)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val message = response.body()
                Log.d("hien", "success8: $message")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("hien", "success9: $t")
            }
        })
    }

    private fun getAllUserInfo() {
        accessToken = NaverIdLoginSDK.getAccessToken().toString()

        val getAllUser = RetrofitService.userService.getAllUsers()
        getAllUser.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userList = response.body()
                    userList?.forEach { user ->
                        Log.d("MainActivity", "User: ${user.username}")
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("MainActivity", "User: $t")
            }
        })
    }

    private fun getRandomMerchantUid(): String {
        return "muid_aos_${Date().time}" // 현재 시간을 이용한 랜덤 머천트 UID 생성
    }
}