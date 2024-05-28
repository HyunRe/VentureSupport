package com.example.app.network
import com.example.app.service.AuthService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "http://localhost:8080/"

    private val logging = HttpLoggingInterceptor().apply {
        // 요청과 응답의 본문 내용까지 로그에 포함
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OKHttp 클라이언트를 구성
    // 이 클라이언트는 로깅 인터셉터를 추가하여 네트워크 요청 시 로그가 생성되도록 함
    // cURL을 확인 하기 위해 사용
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    /*
    * 400 Bad Request 반환용: 서버가 클라이언트 오류(예: 잘못된 요청 구문,
    * 유효하지 않은 요청 메시지 프레이밍, 또는 변조된 요청 라우팅) 를 감지해
    * 요청을 처리할 수 없거나, 하지 않는다는 것이다.
OkHttp interceptor란?
HTTP 요청 및 응답 데이터를 기록하는 OkHttp 인터셉터이다.
    * */


    val ApiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    // ApiService 인터페이스 생성
    val apiService: ApiService = getretrofit().create(ApiService::class.java)
}
