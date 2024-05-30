package com.example.venturesupport

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

// 데이터 클래스로 Order 정보 모델링
data class Order(
    val userId: Int,
    val username: String,
    val email: String,
    val userPhoneNumber: String,
    val lat: Double,
    val lng: Double,
    val userPassword: String,
    val inventoryQuantity: String
)

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var mapView: MapView
    private val polylines = mutableListOf<PolylineOverlay>()
    private val clientId = "5gp8c3chqt"
    private val clientSecret = "OZt0kZU48KJrSXvhDpgafwJe7Jucu8ilfdc5cGHG"
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private val orders = ArrayList<Order>() // Order 정보를 담을 ArrayList
    private val schedules = ArrayList<Order>() // 스케줄을 담을 ArrayList

    private val warehouseAddresses = listOf( // 창고 주소
        "서울특별시 강남구 테헤란로 123",
        "서울특별시 종로구 혜화로 24",
        "서울특별시 마포구 월드컵로10길 50"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation { currentLatLng ->
                currentLatLng?.let {
                    val currentLocationMarker = Marker()
                    currentLocationMarker.position = currentLatLng
                    currentLocationMarker.icon = OverlayImage.fromResource(R.drawable.marker_mylocation)
                    currentLocationMarker.map = naverMap
                }
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation { currentLatLng ->
                currentLatLng?.let {
                    val cameraUpdate = CameraUpdate.scrollTo(it)
                    naverMap.moveCamera(cameraUpdate)
                }
            }
        }

        val addresses = listOf( // 거래처 주소
            "서울특별시 성북구 삼선교로16길 116",
            "서울특별시 노원구 동일로 1414",
            "서울특별시 중구 을지로 39",
            "서울특별시 강남구 테헤란로 152",
            "서울특별시 광진구 능동로 216"
        )

        addresses.forEach { address ->
            getLatLngFromAddress(address) { latLng ->
                latLng?.let {
                    addMarker(it, address, R.drawable.marker_supplier_location, ContextCompat.getColor(this, R.color.green))
                }
            }
        }

        warehouseAddresses.forEach { address ->
            getLatLngFromAddress(address) { latLng ->
                latLng?.let {
                    addMarker(it, address, R.drawable.marker_warehouse_location, ContextCompat.getColor(this, R.color.purple))
                }
            }
        }
    }

    private fun addMarker(latLng: LatLng, address: String, iconResId: Int, pathColor: Int) {
        // Marker 클릭 이벤트 처리
        val marker = Marker()
        marker.position = latLng
        marker.icon = OverlayImage.fromResource(iconResId)
        marker.map = naverMap
        marker.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getLastLocation { currentLatLng ->
                    currentLatLng?.let {
                        showConfirmationDialog(address, latLng, pathColor, it)
                    }
                }
            }
            true
        }
    }

    private fun showConfirmationDialog(address: String, latLng: LatLng, pathColor: Int, currentLatLng: LatLng) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("스케줄 추가")
        builder.setMessage("스케줄 리스트에 추가하시겠습니까?")
        builder.setPositiveButton("확인") { dialog, _ ->
            getDirections(currentLatLng, latLng) { path ->
                path?.let {
                    drawPath(it, pathColor)
                }
            }
            getOrderInfo(address, latLng) { order ->
                order?.let {
                    // 스케줄 리스트에 추가
                    schedules.add(it) // schedules는 스케줄을 담는 ArrayList<Order>입니다.
                    Toast.makeText(this, "스케줄 리스트에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun getOrderInfo(address: String, latLng: LatLng, callback: (Order?) -> Unit) {
        // 여기에 주문 정보를 가져오는 코드를 작성하세요.
        // 임시로 더미 데이터를 사용하거나 실제 데이터베이스에서 가져오는 코드를 작성하세요.
        // 다음은 임시로 더미 데이터를 사용하는 예시 코드입니다.
        val order = Order(
            userId = 1, // 예시 값
            username = "John Doe", // 예시 값
            email = "john@example.com", // 예시 값
            userPhoneNumber = "123-456-7890", // 예시 값
            lat = latLng.latitude, // 해당 위치의 위도
            lng = latLng.longitude, // 해당 위치의 경도
            userPassword = "password", // 예시 값
            inventoryQuantity = "10" // 예시 값
        )
        callback(order)
    }

    private fun getLastLocation(callback: (LatLng?) -> Unit) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            val locationManager = getSystemService(LOCATION_SERVICE) as android.location.LocationManager
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

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MapActivity, "경로를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                    callback(null)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    try {
                        val json = JSONObject(responseData)
                        val route = json.getJSONObject("route")
                        val trafast = route.getJSONArray("trafast")
                        val path = trafast.getJSONObject(0).getJSONArray("path")
                        val latLngList = mutableListOf<LatLng>()
                        for (i in 0 until path.length()) {
                            val point = path.getJSONArray(i)
                            val lat = point.getDouble(1)
                            val lng = point.getDouble(0)
                            latLngList.add(LatLng(lat, lng))
                        }
                        runOnUiThread {
                            callback(latLngList)
                        }
                    } catch (e: JSONException) {
                        runOnUiThread {
                            Toast.makeText(this@MapActivity, "경로 데이터를 파싱하는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                            callback(null)
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MapActivity, "경로를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                        callback(null)
                    }
                }
            }
        })
    }

    private fun drawPath(latLngList: List<LatLng>, pathColor: Int) {
        val polyline = PolylineOverlay()
        polyline.coords = latLngList
        polyline.color = pathColor
        polyline.width = 20
        polyline.map = naverMap
        polylines.add(polyline)
    }

    private fun getLatLngFromAddress(address: String, callback: (LatLng?) -> Unit) {
        val url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=${address}"
        val request = Request.Builder()
            .url(url)
            .addHeader("X-NCP-APIGW-API-KEY-ID", clientId)
            .addHeader("X-NCP-APIGW-API-KEY", clientSecret)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MapActivity, "주소를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                    callback(null)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    try {
                        val json = JSONObject(responseData)
                        val addresses = json.getJSONArray("addresses")
                        if (addresses.length() > 0) {
                            val address = addresses.getJSONObject(0)
                            val lat = address.getDouble("y")
                            val lng = address.getDouble("x")
                            val latLng = LatLng(lat, lng)
                            runOnUiThread {
                                callback(latLng)
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(this@MapActivity, "주소를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                                callback(null)
                            }
                        }
                    } catch (e: JSONException) {
                        runOnUiThread {
                            Toast.makeText(this@MapActivity, "주소 데이터를 파싱하는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                            callback(null)
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MapActivity, "주소를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                        callback(null)
                    }
                }
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mapView.getMapAsync(this)
            } else {
                Toast.makeText(this, "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
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