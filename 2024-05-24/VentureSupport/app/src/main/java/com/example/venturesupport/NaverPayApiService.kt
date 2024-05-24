package com.example.venturesupport

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface NaverPayApiInterface {
    @FormUrlEncoded
    @POST("payment")
    fun makePayment(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Field("orderId") orderId: String,
        @Field("price") price: Int
    ): Call<Void>
}

class NaverPayApiService {
    private val BASE_URL = "https://api.naver.com/v1/payments/"  // 실제 API 엔드포인트로 변경하세요
    private val CLIENT_ID = "YOUR_CLIENT_ID"  // 네이버에서 발급받은 클라이언트 ID
    private val CLIENT_SECRET = "YOUR_CLIENT_SECRET"  // 네이버에서 발급받은 시크릿 키

    private val apiInterface: NaverPayApiInterface

    init {
        val httpClient = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        apiInterface = retrofit.create(NaverPayApiInterface::class.java)
    }

    fun makePayment(orderId: String, price: Int, callback: (Boolean, String?) -> Unit) {
        val call = apiInterface.makePayment(CLIENT_ID, CLIENT_SECRET, orderId, price)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false, t.message)
            }
        })
    }
}
