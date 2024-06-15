package com.example.loginvianaver.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.loginvianaver.modell.User
import com.iamport.sdk.data.sdk.*
import com.iamport.sdk.domain.core.Iamport
import com.iamport.sdk.domain.utils.Event
import org.json.JSONException
import org.json.JSONObject
import java.util.*

// ViewModel 클래스 정의, ViewModel은 앱의 UI 관련 데이터를 저장하고 관리합니다.
class ViewModel : ViewModel() {

    // PG(결제대행사) 관련 변수 선언
    lateinit var pg: PG
    // 결제 방법 관련 변수 선언
    lateinit var payMethod: PayMethod
    // 사용자 코드
    var userCode: String = ""
    // 결제 명칭
    var paymentName: String = ""
    // 주문 번호
    var merchantUid: String = ""
    // 결제 금액
    var amount: String = ""
    // 카드 다이렉트 코드
    var cardDirectCode: String = ""

    // 결제 결과 콜백을 위한 LiveData 객체
    val resultCallback = MutableLiveData<Event<IamPortResponse>>()

    // ViewModel이 소멸될 때 호출되는 메서드, 여기서는 Iamport SDK를 닫습니다.
    override fun onCleared() {
        Iamport.close()
        super.onCleared()
    }

    /**
     * Iamport SDK에 결제를 요청할 데이터를 구성하는 메서드
     */
    fun createIamPortRequest(): IamPortRequest {
        // 카드 다이렉트 코드가 비어있지 않으면 Card 객체 생성
        val card = if (cardDirectCode.isNotEmpty()) Card(Direct(code = cardDirectCode)) else null

        // IamPortRequest 객체 생성 및 반환, 결제 요청에 필요한 정보를 담고 있음
        return IamPortRequest(
            pg = pg.makePgRawName(pgId = ""),           // 결제대행사 (PG) 이름 설정
            pay_method = payMethod.name,                // 결제 방법 설정
            name = paymentName,                         // 결제 명칭 설정
            merchant_uid = merchantUid,                 // 주문 번호 설정
            amount = amount,                            // 결제 금액 설정
            buyer_name = "남궁안녕",                      // 구매자 이름 설정
            card = card,                                // 카드 다이렉트 정보 설정
            custom_data = """
                {
                  "employees": {
                    "employee": [
                      {
                        "id": "1",
                        "firstName": "Tom",
                        "lastName": "Cruise",
                        "photo": "https://jsonformatter.org/img/tom-cruise.jpg",
                        "cuppingnote": "[\"일\",\"이\",\"삼\",\"사\",\"오\",\"육\",\"칠\"]"
                      },
                      {
                        "id": "2",
                        "firstName": "Maria",
                        "lastName": "Sharapova",
                        "photo": "https://jsonformatter.org/img/Maria-Sharapova.jpg"
                      },
                      {
                        "id": "3",
                        "firstName": "Robert",
                        "lastName": "Downey Jr.",
                        "photo": "https://jsonformatter.org/img/Robert-Downey-Jr.jpg"
                      }
                    ]
                  }
                }
            """.trimIndent()
        )
    }

    // 정기 결제를 위한 임의의 고객 UID 생성 메서드
    private fun getRandomCustomerUid(): String {
        return "mcuid_aos_${Date().time}"
    }

    // 네이버 API를 사용하여 사용자 정보를 가져오는 메서드
    fun getUserInfo(accessToken: String?, context: Context, callback: (User?) -> Unit) {
        // 네이버 사용자 정보 API URL
        val apiUrl = "https://openapi.naver.com/v1/nid/me"
        // Volley 라이브러리를 사용하여 네트워크 요청을 관리하는 RequestQueue 객체 생성
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)

        // 네트워크 요청 객체 생성, 여기서는 GET 요청을 사용하여 사용자 정보를 가져옴
        val stringRequest = object : StringRequest(
            Request.Method.GET, apiUrl,
            // 요청 성공 시 호출되는 리스너
            Response.Listener { response ->

                try {
                    // 응답을 JSON 객체로 변환
                    val jsonObject = JSONObject(response)
                    // JSON 객체에서 'response' 키를 사용하여 사용자 정보 추출
                    val responseObj = jsonObject.getJSONObject("response")

                    // 사용자 정보 추출
                    val id = responseObj.getString("id")
                    val email = responseObj.getString("email")
                    val phone = responseObj.getString("mobile")
                    val username = responseObj.getString("name")

                    // User 객체 생성
                    val user = User(id = 2, email = email, phone = phone, username = username, lat = 999.666, lng = 666.999, password = "666", inventoryQuantity = "100box")
                    // 로그에 사용자 ID 출력
                    Log.d("NaverUserInfo", id)

                    // 콜백 함수 호출하여 사용자 객체 전달
                    callback(user)
                } catch (e: JSONException) {
                    // JSON 파싱 오류 시 로그에 오류 메시지 출력
                    Log.e("NaverUserInfoError", "Error parsing JSON: ${e.message}")
                    // 콜백 함수 호출하여 null 전달
                    callback(null)
                }
            },
            // 요청 실패 시 호출되는 리스너
            Response.ErrorListener { error ->
                // 오류 메시지를 로그에 출력
                Log.e("NaverUserInfoError", "Error fetching user info: ${error.message}")
                // 콜백 함수 호출하여 null 전달
                callback(null)
            }
        ) {
            // 요청 헤더 설정, 여기서는 인증 토큰을 추가
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $accessToken"
                return headers
            }
        }

        // 요청 큐에 네트워크 요청 추가
        requestQueue.add(stringRequest)
    }
}
