package com.example.venturesupport

import android.Manifest
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.test2.R
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PolylineOverlay
import com.naver.maps.map.util.FusedLocationSource
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

/**
 * 지도 화면을 구성하는 Fragment입니다.
 */
class MapFragment : Fragment(), OnMapReadyCallback, NaverMap.OnMapClickListener {

    private lateinit var naverMap: NaverMap  // 네이버 맵 객체
    private lateinit var locationSource: FusedLocationSource  // 위치 소스 객체
    private lateinit var mapView: MapView  // 지도 뷰 객체
    private val polylines = mutableListOf<PolylineOverlay>()  // 경로 오버레이 리스트
    private val clientId = "5gp8c3chqt"  // 네이버 API 클라이언트 ID
    private val clientSecret = "OZt0kZU48KJrSXvhDpgafwJe7Jucu8ilfdc5cGHG"  // 네이버 API 클라이언트 시크릿
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000  // 위치 권한 요청 코드

    private val warehouseAddresses = listOf(
        "서울특별시 강남구 테헤란로 123",
        "서울특별시 종로구 혜화로 24",
        "서울특별시 마포구 월드컵로10길 50"
    )  // 창고 주소 리스트

    private var currentLocation: LatLng? = null  // 현재 위치 변수

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 인플레이션 및 초기화
        val view = inflater.inflate(R.layout.map, container, false)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        mapView = view.findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // 위치 권한 요청
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        // 경로 삭제 버튼 클릭 리스너 설정
        val deletePathButton = view.findViewById<Button>(R.id.btn_delete_path)
        deletePathButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        return view
    }

    /**
     * 지도 준비 완료 시 호출됩니다.
     */
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.setOnMapClickListener(this)  // 맵 클릭 리스너 설정

        // 위치 권한이 허용된 경우, 현재 위치를 가져와서 지도에 표시합니다.
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation { currentLatLng ->
                currentLatLng?.let {
                    currentLocation = it
                    val currentLocationMarker = Marker()
                    currentLocationMarker.position = currentLatLng
                    currentLocationMarker.icon = OverlayImage.fromResource(R.drawable.marker_mylocation)
                    currentLocationMarker.map = naverMap
                }
            }
        }

        // 지도 초기 카메라 위치 설정
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation { currentLatLng ->
                currentLatLng?.let {
                    currentLocation = it
                    val cameraUpdate = CameraUpdate.scrollTo(it)
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

        addresses.forEach { address ->
            getLatLngFromAddress(address) { latLng ->
                latLng?.let {
                    addMarker(it, address, R.drawable.marker_supplier_location, ContextCompat.getColor(requireContext(), R.color.green))
                }
            }
        }

        warehouseAddresses.forEach { address ->
            getLatLngFromAddress(address) { latLng ->
                latLng?.let {
                    addMarker(it, address, R.drawable.marker_warehouse_location, ContextCompat.getColor(requireContext(), R.color.purple))
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
            currentLocation = latLng
            true
        }
    }

    /**
     * 마지막 위치를 가져옵니다.
     */
    private fun getLastLocation(callback: (LatLng?) -> Unit) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            val locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            var location: Location? = null
            if (isGpsEnabled || isNetworkEnabled) {
                if (isGpsEnabled) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }
                if (isNetworkEnabled && location == null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }
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

    private fun clearAllPaths() {
        runOnUiThread {
            polylines.forEach { it.map = null }
            polylines.clear()
        }
    }

    override fun onMapClick(pointF: PointF, latLng: LatLng) {
        currentLocation = latLng // 클릭된 위치를 현재 위치로 업데이트
        val currentLocationMarker = Marker()
        currentLocationMarker.position = latLng
        currentLocationMarker.icon = OverlayImage.fromResource(R.drawable.marker_mylocation)
        currentLocationMarker.map = naverMap

        // 이전 위치와 현재 위치 간의 경로를 그림
        currentLocation?.let { currentLatLng ->
            getDirections(currentLatLng, latLng) { path ->
                path?.let {
                    drawPath(it, ContextCompat.getColor(this, R.color.green))
                }
            }
        }
    }

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

