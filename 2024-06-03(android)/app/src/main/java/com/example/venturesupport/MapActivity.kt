package com.example.venturesupport

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PolylineOverlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.MapView
import com.naver.maps.map.overlay.OverlayImage
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MapActivity : AppCompatActivity(), OnMapReadyCallback, NaverMap.OnMapClickListener {

    private lateinit var naverMap: NaverMap  // 네이버 맵 객체
    private lateinit var locationSource: FusedLocationSource   // 현재 위치를 제공하는 소스 객체
    private lateinit var mapView: MapView  // 지도 뷰 객체
    private val polylines = mutableListOf<PolylineOverlay>()  // 경로 오버레이 리스트: 지도에 표시된 경로를 저장하는 리스트
    private val clientId = "5gp8c3chqt"  // 네이버 API 클라이언트 ID
    private val clientSecret = "OZt0kZU48KJrSXvhDpgafwJe7Jucu8ilfdc5cGHG"  // 네이버 API 클라이언트 시크릿
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000  // 위치 권한 요청 코드

    private val warehouseAddresses = listOf(
        "서울특별시 강남구 테헤란로 123",
        "서울특별시 종로구 혜화로 24",
        "서울특별시 마포구 월드컵로10길 50"
    )// 창고 주소 리스트

    private var currentLocation: LatLng? = null // 현재 위치 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)
        // 위치 소스 초기화
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        // 지도 뷰 초기화 및 생성
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // 위치 권한 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        // '경로 삭제' 버튼 클릭 시 확인 다이얼로그 표시
        val deletePathButton = findViewById<Button>(R.id.btn_delete_path)
        deletePathButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }
    /**
     * 지도 준비 완료 시 호출됩니다.
     */
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.setOnMapClickListener(this) // 맵 클릭 리스너 설정: 초기 지도 화면 출력 함수 호출
        // 위치 권한이 허용된 경우, 현재 위치를 가져와서 지도에 표시합니다.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation { currentLatLng -> //최근위치 확보
                currentLatLng?.let {
                    currentLocation = it // 초기 위치 설정
                    val currentLocationMarker = Marker()
                    currentLocationMarker.position = currentLatLng
                    currentLocationMarker.icon = OverlayImage.fromResource(R.drawable.marker_mylocation)
                    currentLocationMarker.map = naverMap
                }
            }
        }
        // 카메라를 현재 위치로 이동
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation { currentLatLng ->// 최근위치 확보
                currentLatLng?.let {
                    currentLocation = it // 초기 위치 설정
                    val cameraUpdate = CameraUpdate.scrollTo(it) //갱신된 이용자 (이동 시) 현재 위치값 저장 변수
                    naverMap.moveCamera(cameraUpdate)
                }
            }
        }

        // 주소 리스트를 순회하면서 마커를 추가합니다.
        val addresses = listOf(
            "서울특별시 성북구 삼선교로16길 116",
            "서울특별시 노원구 동일로 1414",
            "서울특별시 중구 을지로 39",
            "서울특별시 강남구 테헤란로 152",
            "서울특별시 광진구 능동로 216"
        )

        // 각 주소에 대해 마커를 추가
        addresses.forEach { address ->
            getLatLngFromAddress(address) { latLng ->
                latLng?.let {
                    addMarker(it, address, R.drawable.marker_supplier_location, ContextCompat.getColor(this, R.color.green))
                }
            }
        }

        // 창고 주소에 대해 마커를 추가
        warehouseAddresses.forEach { address ->
            getLatLngFromAddress(address) { latLng ->
                latLng?.let {
                    addMarker(it, address, R.drawable.marker_warehouse_location, ContextCompat.getColor(this, R.color.purple))
                }
            }
        }
    }

    /**
     * 지도에 마커를 추가합니다.
     */
    private fun addMarker(latLng: LatLng, address: String, iconResId: Int, pathColor: Int) {
        val marker = Marker()
        marker.position = latLng
        marker.icon = OverlayImage.fromResource(iconResId)
        marker.map = naverMap
        marker.setOnClickListener {
            currentLocation?.let { currentLatLng ->
                getDirections(currentLatLng, latLng) { path ->
                    path?.let {
                        drawPath(it, pathColor)
                    }
                }
            }
            currentLocation = latLng // 현재 위치를 클릭된 아이콘의 위치로 업데이트
            true
        }
    }

    /**
     * 마지막 위치를 가져옵니다.
     */
    private fun getLastLocation(callback: (LatLng?) -> Unit) {
        // 위치 권한이 허용된 경우
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            val locationManager = getSystemService(LOCATION_SERVICE) as android.location.LocationManager
            // GPS와 네트워크의 활성화 상태 확인
            val isGpsEnabled = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)

            var location: Location? = null
            // GPS 또는 네트워크가 활성화된 경우 위치를 가져옴
            if (isGpsEnabled || isNetworkEnabled) {
                if (isGpsEnabled) {
                    location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER)
                }
                if (isNetworkEnabled && location == null) {
                    location = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER)
                }
                // 위치 정보를 LatLng 객체로 변환하여 콜백 함수 호출
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    callback(latLng)
                } ?: callback(null)
            } else {
                callback(null)
            }
        } else {
            callback(null)
        }
    }

    /**
     * 경로 요청 URL을 생성합니다.
     */
    private fun getDirectionsUrl(startLatLng: LatLng, endLatLng: LatLng): String {
        return "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving" +
                "?start=${startLatLng.longitude},${startLatLng.latitude}" +
                "&goal=${endLatLng.longitude},${endLatLng.latitude}" +
                "&option=trafast"
    }

    /**
     * 경로를 요청합니다.
     */
    private fun getDirections(startLatLng: LatLng, endLatLng: LatLng, callback: (List<LatLng>?) -> Unit) {
        val url = getDirectionsUrl(startLatLng, endLatLng)
        val request = Request.Builder()
            .url(url)
            .addHeader("X-NCP-APIGW-API-KEY-ID", clientId)
            .addHeader("X-NCP-APIGW-API-KEY", clientSecret)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonResponse = JSONObject(response.body?.string() ?: "")
                    try {
                        val route = jsonResponse.getJSONObject("route").getJSONArray("trafast")
                        if (route.length() > 0) {
                            val path = route.getJSONObject(0).getJSONArray("path")
                            val pathPoints = mutableListOf<LatLng>()
                            for (i in 0 until path.length()) {
                                val point = path.getJSONArray(i)
                                val lat = point.getDouble(1)
                                val lng = point.getDouble(0)
                                pathPoints.add(LatLng(lat, lng))
                            }
                            callback(pathPoints)
                        } else {
                            callback(null)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
        })
    }

    /**
     * 지도에 경로를 그립니다.
     */
    private fun drawPath(path: List<LatLng>, color: Int) {
        runOnUiThread {
            val polylineOverlay = PolylineOverlay()
            polylineOverlay.coords = path
            polylineOverlay.color = color
            polylineOverlay.width = 20
            polylineOverlay.map = naverMap
            polylines.add(polylineOverlay)
        }
    }
    /**
     * 주소로부터 위도, 경도 정보를 가져옵니다.
     */
    private fun getLatLngFromAddress(address: String, callback: (LatLng?) -> Unit) {
        val url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=$address"
        val request = Request.Builder()
            .url(url)
            .addHeader("X-NCP-APIGW-API-KEY-ID", clientId)
            .addHeader("X-NCP-APIGW-API-KEY", clientSecret)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonResponse = JSONObject(response.body?.string() ?: "")
                    val addresses = jsonResponse.getJSONArray("addresses")
                    if (addresses.length() > 0) {
                        val addressObject = addresses.getJSONObject(0)
                        val lat = addressObject.getDouble("y")
                        val lng = addressObject.getDouble("x")
                        val latLng = LatLng(lat, lng)
                        runOnUiThread {
                            callback(latLng)
                        }
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
        })
    }
    /**
     * 경로 삭제 확인 다이얼로그를 표시합니다.
     */
    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("경로 삭제")
            .setMessage("정말로 모든 경로를 삭제하시겠습니까?")
            .setPositiveButton("삭제") { _, _ ->
                clearAllPaths()
            }
            .setNegativeButton("취소", null)
            .show()
    }

    /**
     * 모든 경로를 삭제합니다.
     */
    private fun clearAllPaths() {
        runOnUiThread {
            polylines.forEach { it.map = null }
            polylines.clear()
        }
    }

    /**
     * 지도 클릭 시 호출됩니다.
     */
    override fun onMapClick(pointF: PointF, latLng: LatLng) {
        currentLocation = latLng // 클릭된 위치를 현재 위치로 업데이트
        val currentLocationMarker = Marker()
        currentLocationMarker.position = latLng
        currentLocationMarker.icon = OverlayImage.fromResource(R.drawable.marker_mylocation)
        currentLocationMarker.map = naverMap

        // 이전 위치와 현재 위치 간의 경로를 선으로 그림
        /*getDirections를 통해 path에 해당하는 위치들의 위경도값을 얻어, 선을 그을 drawPath로 전달
        * */
        currentLocation?.let { currentLatLng ->
            getDirections(currentLatLng, latLng) { path ->
                path?.let {
                    drawPath(it, ContextCompat.getColor(this, R.color.green))
                }
            }
        }
    }

    /**
     * 권한 요청 결과를 처리합니다.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation { currentLatLng ->
                    currentLatLng?.let {
                        val cameraUpdate = CameraUpdate.scrollTo(it)
                        naverMap.moveCamera(cameraUpdate)
                    }
                }
            }
        }
    }

    // 액티비티 생명주기 메서드
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
}

