package com.example.myactivity.client
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.myactivity.data.RetrofitClient
import com.example.myactivity.data.model.VehicleInventory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleCli(private val context: Context) {

        private val apiService = RetrofitClient.vehicleService

        // 모든 차량 재고 조회
        fun getAllVehicleInventory() {
            apiService.getAllVehicleInventory().enqueue(object : Callback<List<VehicleInventory>> {
                override fun onResponse(call: Call<List<VehicleInventory>>, response: Response<List<VehicleInventory>>) {
                    if (response.isSuccessful) {
                        val vehicleInventoryList = response.body()
                        Log.d("VehicleInventoryClient", "모든 차량 재고: $vehicleInventoryList")
                        Toast.makeText(context, "모든 차량 재고를 불러왔습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("VehicleInventoryClient", "응답 실패")
                        Toast.makeText(context, "차량 재고 목록을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<VehicleInventory>>, t: Throwable) {
                    Log.e("VehicleInventoryClient", "네트워크 오류", t)
                    Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // 차량 재고 ID로 특정 차량 재고 조회
        fun getVehicleInventoryById(id: Int) {
            apiService.getVehicleInventoryById(id).enqueue(object : Callback<VehicleInventory> {
                override fun onResponse(call: Call<VehicleInventory>, response: Response<VehicleInventory>) {
                    if (response.isSuccessful) {
                        val vehicleInventory = response.body()
                        Log.d("VehicleInventoryClient", "조회된 차량 재고: $vehicleInventory")
                        Toast.makeText(context, "차량 재고를 불러왔습니다: $vehicleInventory", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("VehicleInventoryClient", "응답 실패")
                        Toast.makeText(context, "차량 재고를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<VehicleInventory>, t: Throwable) {
                    Log.e("VehicleInventoryClient", "네트워크 오류", t)
                    Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // 새로운 차량 재고 생성
        fun createVehicleInventory(vehicleInventory: VehicleInventory) {
            apiService.createVehicleInventory(vehicleInventory).enqueue(object : Callback<VehicleInventory> {
                override fun onResponse(call: Call<VehicleInventory>, response: Response<VehicleInventory>) {
                    if (response.isSuccessful) {
                        val createdVehicleInventory = response.body()
                        Log.d("VehicleInventoryClient", "생성된 차량 재고: $createdVehicleInventory")
                        Toast.makeText(context, "차량 재고가 생성되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("VehicleInventoryClient", "응답 실패")
                        Toast.makeText(context, "차량 재고 생성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<VehicleInventory>, t: Throwable) {
                    Log.e("VehicleInventoryClient", "네트워크 오류", t)
                    Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // 기존 차량 재고 업데이트
        fun updateVehicleInventory(id: Int, vehicleInventory: VehicleInventory) {
            apiService.updateVehicleInventory(id, vehicleInventory).enqueue(object : Callback<VehicleInventory> {
                override fun onResponse(call: Call<VehicleInventory>, response: Response<VehicleInventory>) {
                    if (response.isSuccessful) {
                        val updatedVehicleInventory = response.body()
                        Log.d("VehicleInventoryClient", "업데이트된 차량 재고: $updatedVehicleInventory")
                        Toast.makeText(context, "차량 재고가 업데이트되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("VehicleInventoryClient", "응답 실패")
                        Toast.makeText(context, "차량 재고 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<VehicleInventory>, t: Throwable) {
                    Log.e("VehicleInventoryClient", "네트워크 오류", t)
                    Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // 차량 재고 삭제
        fun deleteVehicleInventory(id: Int) {
            apiService.deleteVehicleInventory(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("VehicleInventoryClient", "차량 재고가 삭제되었습니다.")
                        Toast.makeText(context, "차량 재고가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("VehicleInventoryClient", "응답 실패")
                        Toast.makeText(context, "차량 재고 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("VehicleInventoryClient", "네트워크 오류", t)
                    Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

