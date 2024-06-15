package com.example.venturesupport

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.WarehouseRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WarehouseRegistrationActivity: AppCompatActivity() {
    private val binding: WarehouseRegistrationBinding by lazy {
        WarehouseRegistrationBinding.inflate(layoutInflater)
    }
    private var intentUser: User? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentUser = intent.getParcelableExtra("intentUser") ?: error("User 정보가 없습니다.")

        binding.checkButton.setOnClickListener {
            val warehouseName = binding.warehouseNameEdit.text.toString()
            val warehouseLocation = binding.warehouseLocationEdit.text.toString()

            val warehouse = Warehouse(
                warehouseId = null,
                user = intentUser!!,
                warehouseName = warehouseName,
                warehouseLocation = warehouseLocation
            )

            createWarehouse(warehouse)

            val intent = Intent(this, WarehouseActivity::class.java).apply {
                putExtra("intentUser", intentUser)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun createWarehouse(warehouse: Warehouse) {
        val call = RetrofitService.warehouseService.createWarehouse(warehouse)
        call.enqueue(object : Callback<Warehouse> {
            override fun onResponse(call: Call<Warehouse>, response: Response<Warehouse>) {
                if (response.isSuccessful) {
                    val createdWarehouse = response.body()
                    println("Warehouse 생성 성공: $createdWarehouse")
                } else {
                    println("Warehouse 생성 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Warehouse>, t: Throwable) {
                println("네트워크 요청 실패: ${t.message}")
            }
        })
    }
}
