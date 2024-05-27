package com.example.venturesupport.api

import com.example.venturesupport.model.DirectionsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverDirectionsService {
    @GET("map-direction/v1/driving")
    fun getDrivingRoute(
        @Query("start") start: String,
        @Query("goal") goal: String,
        @Query("option") option: String = "trafast"
    ): Call<DirectionsResponse>
}