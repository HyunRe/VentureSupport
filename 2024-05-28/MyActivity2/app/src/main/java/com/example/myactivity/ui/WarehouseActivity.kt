package com.example.myactivity.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.data.model.User
import com.example.myactivity.data.model.Warehouse
import com.example.myactivity.databinding.ActivityWarehouseBinding

class WarehouseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWarehouseBindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarehouseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 조회 버튼 클릭 시
        binding.getWarehouseBtn.setOnClickListener {
            val warehouseId = binding.warehouseIdWare.text.toString().toIntOrNull()
            if (warehouseId != null) {
                getWarehouseById(warehouseId)
            } else {
                Toast.makeText(this, "Warehouse ID를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 생성 버튼 클릭 시
        binding.createWarehouseBtn.setOnClickListener {
            val warehouseName = binding.warehouseNameWare.text.toString()
            val warehouseLocation = binding.warehouseLocationWare.text.toString()
            val userId = binding.userIdWare.text.toString().toIntOrNull()
            if (warehouseName.isNotEmpty() && warehouseLocation.isNotEmpty() && userId != null) {
                val warehouse = Warehouse(0, User(userId), warehouseName, warehouseLocation)
                createWarehouse(warehouse)
            } else {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 업데이트 버튼 클릭 시
        binding.updateWarehouseBtn.setOnClickListener {
            val warehouseId = binding.warehouseIdWare.text.toString().toIntOrNull()
            val warehouseName = binding.warehouseNameWare.text.toString()
            val warehouseLocation = binding.warehouseLocationWare.text.toString()
            val userId = binding.userIdWare.text.toString().toIntOrNull()
            if (warehouseId != null && warehouseName.isNotEmpty() && warehouseLocation.isNotEmpty() && userId != null) {
                val warehouse = Warehouse(warehouseId, User(userId), warehouseName, warehouseLocation)
                updateWarehouse(warehouseId, warehouse)
            } else {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 삭제 버튼 클릭 시
        binding.deleteWarehouseBtn.setOnClickListener {
            val warehouseId = binding.warehouseIdWare.text.toString().toIntOrNull()
            if (warehouseId != null) {
                deleteWarehouse(warehouseId)
            } else {
                Toast.makeText(this, "Warehouse ID를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
/*
    // 창고 조회
    private fun getWarehouseById(id: Int) {
        RetrofitClient.warehouseService.getWarehouseById(id).enqueue(object : Callback<Warehouse> {
            override fun onResponse(call: Call<Warehouse>, response: Response<Warehouse>) {
                if (response.isSuccessful) {
                    val warehouse = response.body()
                    binding.warehouseResultWare.text = "조회된 창고: $warehouse"
                } else {
                    Log.e("WarehouseClient", "응답 실패")
                    Toast.makeText(this@MainActivity, "창고를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Warehouse>, t: Throwable) {
                Log.e("WarehouseClient", "네트워크 오류", t)
                Toast.makeText(this@MainActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 창고 생성
    private fun createWarehouse(warehouse: Warehouse) {
        RetrofitClient.warehouseService.createWarehouse(warehouse).enqueue(object : Callback<Warehouse> {
            override fun onResponse(call: Call<Warehouse>, response: Response<Warehouse>) {
                if (response.isSuccessful) {
                    val createdWarehouse = response.body()
                    binding.warehouseResultWare.text = "생성된 창고: $createdWarehouse"
                } else {
                    Log.e("WarehouseClient", "응답 실패")
                    Toast.makeText(this, "창고 생성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Warehouse>, t: Throwable) {
                Log.e("WarehouseClient", "네트워크 오류", t)
                Toast.makeText(this, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 창고 업데이트
    private fun updateWarehouse(id: Int, warehouse: Warehouse) {
        RetrofitClient.warehouseService.updateWarehouse(id, warehouse).enqueue(object : Callback<Warehouse> {
            override fun onResponse(call: Call<Warehouse>, response: Response<Warehouse>) {
                if (response.isSuccessful) {
                    val updatedWarehouse = response.body()
                    binding.warehouseResultWare.text = "업데이트된 창고: $updatedWarehouse"
                } else {
                    Log.e("WarehouseClient", "응답 실패")
                    Toast.makeText(this@MainActivity, "창고 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Warehouse>, t: Throwable) {
                Log.e("WarehouseClient", "네트워크 오류", t)
                Toast.makeText(this@MainActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 창고 삭제
    private fun deleteWarehouse(id: Int) {
        RetrofitClient.warehouseService.deleteWarehouse(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    binding.warehouseResultWare.text = "창고가 삭제되었습니다."
                } else {
                    Log.e("WarehouseClient", "응답 실패")
                    Toast.makeText(this@MainActivity, "창고 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("WarehouseClient", "네트워크 오류", t)
                Toast.makeText(this@MainActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }*/
}
