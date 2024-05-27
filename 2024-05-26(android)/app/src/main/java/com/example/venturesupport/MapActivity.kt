package com.example.venturesupport

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.api.NaverDirectionsService
import com.example.venturesupport.model.DirectionsResponse
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.util.MarkerIcons
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) { // 권한 요청을 거부한 경우
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        // 현재 위치 트래킹 활성화
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        // 현재 위치로 지도 이동
        val latLng = LatLng(37.5666102, 126.9783881) // 서울시청 좌표
        val cameraUpdate = CameraUpdate.scrollTo(latLng)
        naverMap.moveCamera(cameraUpdate)

        // 시작점 좌표
        val startLatLng = LatLng(37.5666102, 126.9783881) // 서울시청 좌표
        // 도착점 좌표
        val destinationLatLng = LatLng(37.497942, 127.027621) // 강남역 좌표

        // 시작점 마커 추가
        val startMarker = Marker()
        startMarker.position = startLatLng
        startMarker.iconTintColor = Color.BLUE // 시작점 아이콘 색상을 파란색으로 설정
        startMarker.icon = MarkerIcons.BLACK // 시작점 아이콘을 기본 마커 아이콘으로 설정
        startMarker.width = 50 // 시작점 아이콘의 크기를 50px로 설정
        startMarker.height = 50
        startMarker.map = naverMap

        // 도착점 마커 추가
        val destinationMarker = Marker()
        destinationMarker.position = destinationLatLng
        destinationMarker.iconTintColor = Color.RED // 도착점 아이콘 색상을 빨간색으로 설정
        destinationMarker.icon = MarkerIcons.BLACK // 도착점 아이콘을 기본 마커 아이콘으로 설정
        destinationMarker.width = 50 // 도착점 아이콘의 크기를 50px로 설정
        destinationMarker.height = 50
        destinationMarker.map = naverMap

        // 경로 그리기
        drawPath(startLatLng, destinationLatLng)
    }

    private fun drawPath(startLatLng: LatLng, destinationLatLng: LatLng) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://naveropenapi.apigw.ntruss.com/")
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-NCP-APIGW-API-KEY-ID", "5gp8c3chqt")  // 여기에 발급받은 클라이언트 ID를 넣으세요
                    .addHeader("X-NCP-APIGW-API-KEY", "OZt0kZU48KJrSXvhDpgafwJe7Jucu8ilfdc5cGHG") // 여기에 발급받은 클라이언트 Secret을 넣으세요
                    .build()
                chain.proceed(request)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(NaverDirectionsService::class.java)

        val call = service.getDrivingRoute(
            "${startLatLng.longitude},${startLatLng.latitude}",
            "${destinationLatLng.longitude},${destinationLatLng.latitude}"
        )

        call.enqueue(object : Callback<DirectionsResponse> {
            override fun onResponse(call: Call<DirectionsResponse>, response: Response<DirectionsResponse>) {
                val route = response.body()?.route?.trafast?.firstOrNull()
                if (route != null) {
                    val path = route.path.map { LatLng(it[1], it[0]) }
                    val pathOverlay = PathOverlay()
                    pathOverlay.coords = path
                    pathOverlay.color = Color.parseColor("#A2F403")
                    pathOverlay.outlineColor = Color.parseColor("#599468")
                    pathOverlay.width = 12 // 경로선의 너비를 12px로 설정
                    pathOverlay.map = naverMap
                } else {
                    Toast.makeText(this@MapActivity, "경로를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                Toast.makeText(this@MapActivity, "경로 요청 실패: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}