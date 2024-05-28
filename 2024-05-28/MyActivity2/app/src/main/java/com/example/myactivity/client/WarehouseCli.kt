package com.example.myactivity.client

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.myactivity.data.model.Warehouse
import com.example.myactivity.data.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WarehouseCli(private val context: Context) {

    private val apiService = RetrofitClient.apiService

    // 모든 창고 조회
    fun getAllWarehouses() {
        apiService.getAllWarehouses().enqueue(object : Callback<List<Warehouse>> {
            override fun onResponse(call: Call<List<Warehouse>>, response: Response<List<Warehouse>>) {
                if (response.isSuccessful) {
                    val warehouses = response.body()
                    Log.d("WarehouseClient", "모든 창고: $warehouses")
                    Toast.makeText(context, "모든 창고를 불러왔습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("WarehouseClient", "응답 실패")
                    Toast.makeText(context, "창고 목록을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Warehouse>>, t: Throwable) {
                Log.e("WarehouseClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 창고 ID로 특정 창고 조회
    fun getWarehouseById(id: Int) {
        apiService.getWarehouseById(id).enqueue(object : Callback<Warehouse> {
            override fun onResponse(call: Call<Warehouse>, response: Response<Warehouse>) {
                if (response.isSuccessful) {
                    val warehouse = response.body()
                    Log.d("WarehouseClient", "조회된 창고: $warehouse")
                    Toast.makeText(context, "창고를 불러왔습니다: $warehouse", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("WarehouseClient", "응답 실패")
                    Toast.makeText(context, "창고를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Warehouse>, t: Throwable) {
                Log.e("WarehouseClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 새로운 창고 생성
    fun createWarehouse(warehouse: Warehouse) {
        apiService.createWarehouse(warehouse).enqueue(object : Callback<Warehouse> {
            override fun onResponse(call: Call<Warehouse>, response: Response<Warehouse>) {
                if (response.isSuccessful) {
                    val createdWarehouse = response.body()
                    Log.d("WarehouseClient", "생성된 창고: $createdWarehouse")
                    Toast.makeText(context, "창고가 생성되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("WarehouseClient", "응답 실패")
                    Toast.makeText(context, "창고 생성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Warehouse>, t: Throwable) {
                Log.e("WarehouseClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 기존 창고 업데이트
    fun updateWarehouse(id: Int, warehouse: Warehouse) {
        apiService.updateWarehouse(id, warehouse).enqueue(object : Callback<Warehouse> {
            override fun onResponse(call: Call<Warehouse>, response: Response<Warehouse>) {
                if (response.isSuccessful) {
                    val updatedWarehouse = response.body()
                    Log.d("WarehouseClient", "업데이트된 창고: $updatedWarehouse")
                    Toast.makeText(context, "창고가 업데이트되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("WarehouseClient", "응답 실패")
                    Toast.makeText(context, "창고 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Warehouse>, t: Throwable) {
                Log.e("WarehouseClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 창고 삭제
    fun deleteWarehouse(id: Int) {
        apiService.deleteWarehouse(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("WarehouseClient", "창고가 삭제되었습니다.")
                    Toast.makeText(context, "창고가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("WarehouseClient", "응답 실패")
                    Toast.makeText(context, "창고 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("WarehouseClient", "네트워크 오류", t)
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
