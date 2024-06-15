package com.example.test2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.AddvehicleinventoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddVehicleInventoryActivity: AppCompatActivity() {
    private val binding: AddvehicleinventoryBinding by lazy {
        AddvehicleinventoryBinding.inflate(layoutInflater)
    }
    private var intentUser: User? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentUser = intent.getParcelableExtra("intentUser")

        // 수정 필요
        binding.checkButton.setOnClickListener {
            val productNameEdit = binding.productNameEdit.text.toString()
            val productQuantityEdit = binding.productQuantityEdit.text.toString()

            val vehicleInventory = VehicleInventory(
                vehicleInventoryId = null,
                user = intentUser!!,
                productName = productNameEdit,
                vehicleInventoryQuantity = productQuantityEdit
            )

            createVehicleInventory(vehicleInventory)
            val intent = Intent(this, VehicleInventorylistActivity::class.java)
            intent.putExtra("intentUser", intentUser)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun createVehicleInventory(vehicleInventory: VehicleInventory) {
        val call = RetrofitService.vehicleInventoryService.createVehicleInventory(vehicleInventory)
        call.enqueue(object : Callback<VehicleInventory> {
            override fun onResponse(call: Call<VehicleInventory>, response: Response<VehicleInventory>) {
                if (response.isSuccessful) {
                    val createdVehicleInventory = response.body()
                    println("VehicleInventory 생성 성공: $createdVehicleInventory")
                } else {
                    println("VehicleInventory 생성 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<VehicleInventory>, t: Throwable) {
                println("네트워크 요청 실패: ${t.message}")
            }
        })
    }

}