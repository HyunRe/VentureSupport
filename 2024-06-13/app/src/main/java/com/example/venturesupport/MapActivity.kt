package com.example.venturesupport

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PolylineOverlay
import com.naver.maps.map.util.FusedLocationSource
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MapActivity : Fragment(), OnMapReadyCallback, NaverMap.OnMapClickListener {

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var mapView: MapView
    private val polylines = mutableListOf<PolylineOverlay>()
    private val clientId = "5gp8c3chqt"
    private val clientSecret = "OZt0kZU48KJrSXvhDpgafwJe7Jucu8ilfdc5cGHG"
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000

    private val warehouseAddresses = listOf(
        "서울특별시 강남구 테헤란로 123",
        "서울특별시 종로구 혜화로 24",
        "서울특별시 마포구 월드컵로10길 50"
    )

    private var currentLocation: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 인플레이션 및 초기화
        val view = inflater.inflate(R.layout.map, container, false)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        mapView = findViewById(R.id.map_view)
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

        // '경로 삭제' 버튼 클릭 시 확인 다이얼로그 표시
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
        naverMap.setOnMapClickListener(this) // 맵 클릭 리스너 설정

        // 위치 권한이 허용된 경우, 현재 위치를 가져와서 지도에 표시합니다.
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation { currentLatLng ->
                currentLatLng?.let {
                    currentLocation = it // 초기 위치 설정
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
                    currentLocation = it // 초기 위치 설정
                    val cameraUpdate = CameraUpdate.scrollTo(it)
                    naverMap.moveCamera(cameraUpdate)
                }
            }
        }

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

    private fun getLastLocation(callback: (LatLng?) -> Unit) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            val locationManager = requireContext().getSystemService(LOCATION_SERVICE) as android.location.LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)

            var location: Location? = null
            if (isGpsEnabled || isNetworkEnabled) {
                if (isGpsEnabled) {
                    location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER)
                }
                if (isNetworkEnabled && location == null) {
                    location = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER)
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

    private fun getDirectionsUrl(startLatLng: LatLng, endLatLng: LatLng): String {
        return "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving" +
                "?start=${startLatLng.longitude},${startLatLng.latitude}" +
                "&goal=${endLatLng.longitude},${endLatLng.latitude}" +
                "&option=trafast"
    }

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
        activity?.runOnUiThread {
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
        AlertDialog.Builder(requireContext())
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
                    drawPath(it, ContextCompat.getColor(requireContext(), R.color.green))
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

