package com.example.loginvianaver.ui

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.loginvianaver.R
import com.example.loginvianaver.modell.User
import com.example.loginvianaver.receiver.MerchantReceiver
import com.example.loginvianaver.service.ApiClient
import com.example.loginvianaver.viewmodel.PaymentResultData.result66
import com.example.loginvianaver.viewmodel.ViewModel
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

class HomeActivity : AppCompatActivity() {

    // UI 요소 선언
    lateinit var btn: Button // 데이터를 전송하는 버튼
    lateinit var btnLogout : Button // 로그아웃 버튼
    lateinit var btnPayment : Button // 결제 버튼
    lateinit var paymentButton : Button // 다른 결제 버튼

    lateinit var payIpayButton : Button // Ipay 결제 버튼
    lateinit var certificationBtn : Button // 인증 버튼
    lateinit var spinner : Spinner // 사용자 코드 선택 스피너
    lateinit var pgSpinner: Spinner // PG 선택 스피너
    lateinit var pgMethod : Spinner // 결제 방법 선택 스피너
    lateinit var name : EditText // 결제자 이름 입력 필드
    lateinit var amount : EditText // 결제 금액 입력 필드
    lateinit var cardDirectCode : EditText // 카드 코드 입력 필드

    private val viewModel: ViewModel by viewModels() // ViewModel 인스턴스
    private val receiver = MerchantReceiver() // 포그라운드 서비스 리시버: MerchantReceiver 인스턴스
    lateinit var accessToken: String // 네이버 로그인 액세스 토큰

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        // 시스템 바 영역 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 초기 설정 및 액션 초기화
        initAction()// UI 요소 초기화
        Iamport.init(this) // Iamport SDK 초기화
        // 사용자 로그인 및 정보 가져오기
        loginuser()// 사용자 로그인
        getAlluserInfo() // 모든 사용자 정보 조회
        // 포그라운드 서비스 리시버 등록
        registForegroundServiceReceiver(this)

        
   
    }
    
    private fun initAction(){
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
            Util.getUserCodeList()// 사용자 코드 리스트 가져오기
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

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무것도 선택되지 않았을 때
            }

        }

        pgSpinner.adapter = pgAdapter
        pgSpinner.onItemSelectedListener = pgSelectListener // PG 선택 리스너 설정

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
        // 사용자 정보 가져오기 / 새로운 사용자 생성 버튼 클릭 리스너
        btn.setOnClickListener {
            getUserInfo(accessToken){
                createnewuser(it!!) // 새로운 사용자 생성
            }
        }
    }

    override fun onStart() {
        // 결제 결과 콜백 리스너 설정
        viewModel.resultCallback.observe(this, EventObserver {
            startActivity(Intent(this@HomeActivity, PaymentResultActivity::class.java)) // 결제 결과 화면으로 이동
        })

        super.onStart()
    }

    // 결제 요청을 처리하는 메서드
    private fun onClickPayment() {
        val userCode = viewModel.userCode
        val request = viewModel.createIamPortRequest()// 결제 요청 생성
        println(request)

        Iamport.payment(userCode, iamPortRequest = request) { callBackListener.result(it) } //결제 처리
    }

    // 웹뷰 모드 결제 요청을 처리하는 메서드
    private fun onClickWebViewModePayment() {
        val userCode = viewModel.userCode
        val request = viewModel.createIamPortRequest()
        Log.i("SAMPLE", "userCode :: $userCode")
        Log.i("SAMPLE", GsonBuilder().setPrettyPrinting().create().toJson(request))

        Iamport.close()
        startActivity(Intent(this, WebViewModeActivity::class.java)) // 웹뷰 모드 결제 화면으로 이동
    }

    // 모바일 웹 모드 결제 요청을 처리하는 메서드
    private fun onClickMobileWebModePayment() {
        Iamport.close()
        startActivity(Intent(this,MobileWebViewModeActivity::class.java)) // 모바일 웹 모드 결제 화면으로 이동
    }

    // 인증 요청을 처리하는 메서드
    fun onClickCertification() {
        val userCode = "iamport"
        val certification = IamPortCertification(
            merchant_uid = getRandomMerchantUid(), // 랜덤한 머천트 UID 생성
            company = "유어포트", // 회사명 설정
        )

        Iamport.certification(userCode, iamPortCertification = certification) { callBackListener.result(it) }
    }

    private fun registForegroundServiceReceiver(context: Context) {

        Iamport.enableChaiPollingForegroundService(enableService = true, enableFailStopButton = true)

        // 포그라운드 서비스 및 포그라운드 서비스 중지 버튼 클릭시 전달받는 broadcast 리시버
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            context.registerReceiver(receiver, IntentFilter().apply {
                addAction(CONST.BROADCAST_FOREGROUND_SERVICE)
                addAction(CONST.BROADCAST_FOREGROUND_SERVICE_STOP)
            }, Context.RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(receiver, IntentFilter().apply {
                addAction(CONST.BROADCAST_FOREGROUND_SERVICE)
                addAction(CONST.BROADCAST_FOREGROUND_SERVICE_STOP)
            })
        }

    }
    // 결제 결과 콜백 리스너
    private val callBackListener = object : ICallbackPaymentResult {
        override fun result(iamPortResponse: IamPortResponse?) {
            val resJson = GsonBuilder().setPrettyPrinting().create().toJson(iamPortResponse)
            Log.i("SAMPLE", "결제 결과 콜백\n$resJson")
            Log.d("minhien", "$iamPortResponse  ")
            result66 = iamPortResponse
            if (iamPortResponse != null) {
                startActivity(Intent(this@HomeActivity,PaymentResultActivity::class.java))
                // 결제 결과 화면으로 이동
                viewModel.resultCallback.postValue(Event(iamPortResponse))
            }
        }
    }
    // PG 선택 리스너
    private val pgSelectListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.pg = PG.values()[position] // 선택된 PG 설정
            pgMethod.adapter = ArrayAdapter(
                this@HomeActivity, android.R.layout.simple_spinner_dropdown_item,
                Util.convertPayMethodNames(PG.values()[position])
            ) // PG에 따른 결제 방법 설정

            pgMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    viewModel.payMethod = Util.getMappingPayMethod(viewModel.pg).elementAt(pgMethod.selectedItemPosition)
                } // 선택된 결제 방법 설정

                override fun onNothingSelected(parent: AdapterView<*>?) { // 아무것도 선택되지 않았을 때
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) { // 아무것도 선택되지 않았을 때
        }
    }

    // 네이버 로그인 액세스 토큰으로 사용자 정보를 조회하는 메서드
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

                    val user = User(id = 2, email = email, phone = phone, username = username, lat = 999.666, lng = 666.999, password = "666", inventoryQuantity = "100box")
                    Log.d("NaverUserInfo", id)

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
                headers["Authorization"] = "Bearer $accessToken" // 액세스 토큰 헤더 설정
                return headers
            }
        }

        requestQueue.add(stringRequest)
    }

    //유저 정보 갱신
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

    // 새로운 사용자를 생성하는 메서드
    private fun createnewuser(user: User){
        val createUser = ApiClient.apiService.createUser(user)
        createUser.enqueue(object : Callback<User>{
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
    // 사용자 로그인을 처리하는 메서드
    private fun loginuser(){
        val user = User(id = null, username = "hiendzvcl1", email = "hiendz1@example.com",lat = 9999.9999,lng = 999999.99, phone = "666" , password = "666", inventoryQuantity = "100")

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
    }

    // 모든 사용자 정보를 조회하는 메서드
    private fun getAlluserInfo(){
        //get user info from naver login
        accessToken = NaverIdLoginSDK.getAccessToken().toString()

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
                Log.d("MainActivity", "User: ${p1}")
            }

        })
    }

    // 랜덤한 머천트 UID를 생성하는 메서드
    private fun getRandomMerchantUid(): String {
        return "muid_aos_${Date().time}"
    }
}