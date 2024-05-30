package com.example.venturesupport

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("path_to_your_php_script")
    fun getSchedules(): Call<List<Schedule>>
}
