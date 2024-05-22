package com.example.venturesupport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var locationSource: FusedLocationSource
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onMapReady(naverMap: NaverMap) {
        naverMap.locationSource = locationSource
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (!locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}

//package com.example.venturesupport
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.naver.maps.map.CameraUpdate
//import com.naver.maps.map.CameraUpdateParams
//import com.naver.maps.map.MapFragment
//import com.naver.maps.map.NaverMap
//import com.naver.maps.map.OnMapReadyCallback
//import com.naver.maps.map.overlay.PathOverlay
//import com.naver.maps.geometry.LatLng
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//class MapActivity : AppCompatActivity(), OnMapReadyCallback {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.map)
//
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as MapFragment?
//            ?: MapFragment.newInstance().also {
//                supportFragmentManager.beginTransaction().add(R.id.map, it).commit()
//            }
//        mapFragment.getMapAsync(this)
//    }
//
//    override fun onMapReady(naverMap: NaverMap) {
//        // 네이버 길찾기 API 사용을 위한 Retrofit 설정
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/v1/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val service = retrofit.create(DirectionService::class.java)
//
//        // 네이버 길찾기 API 호출
//        service.getDirections(
//            "i3o6oasean",
//            "maj1xsCnz4oNC52W8Uup6IkOUiXN0KK4TqBIa4pZ",
//            "127.1054328,37.3595963",
//            "127.1054923,37.3598053"
//        ).enqueue(object : Callback<DirectionsResponse> {
//            override fun onResponse(call: Call<DirectionsResponse>, response: Response<DirectionsResponse>) {
//                if (response.isSuccessful && response.body() != null) {
//                    val pathPoints = response.body()!!.route.traoptimal[0].path.map {
//                        LatLng(it[1], it[0])
//                    }
//                    val path = PathOverlay()
//                    path.coords = pathPoints
//                    path.map = naverMap
//
//                    // 카메라를 경로 중심으로 이동
//                    val cameraUpdate = CameraUpdate.fitBounds(path.bounds, 100)
//                    naverMap.moveCamera(cameraUpdate)
//                }
//            }
//
//            override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
//                t.printStackTrace()
//            }
//        })
//    }
//
//    interface DirectionService {
//        @GET("driving")
//        fun getDirections(
//            @Query("clientId") clientId: String,
//            @Query("clientSecret") clientSecret: String,
//            @Query("start") start: String,
//            @Query("goal") goal: String
//        ): Call<DirectionsResponse>
//    }
//
//    data class DirectionsResponse(
//        val route: Route
//    )
//
//    data class Route(
//        val traoptimal: List<Traoptimal>
//    )
//
//    data class Traoptimal(
//        val summary: Summary,
//        val path: List<List<Double>>
//    )
//
//    data class Summary(
//        val start: Location,
//        val goal: Location,
//        val distance: Int,
//        val duration: Int
//    )
//
//    data class Location(
//        val location: List<Double>
//    )
//}
