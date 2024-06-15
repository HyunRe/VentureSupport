package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.VehicleinventorylistBinding
import com.example.test2.databinding.VehicleinventorylistItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleInventorylistActivity: AppCompatActivity() {
    private val binding: VehicleinventorylistBinding by lazy {
        VehicleinventorylistBinding.inflate(layoutInflater)
    }
    private lateinit var vehicleInventorylistAdapter: VehicleInventorylistAdapter
    private var inverntorytLists = ArrayList<VehicleInventory>()
    private var intentUser: User? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentUser = intent.getParcelableExtra("intentUser")

        getVehicleInventoriesByUserId(intentUser?.userId!!)

        vehicleInventorylistAdapter = VehicleInventorylistAdapter(inverntorytLists)
        vehicleInventorylistAdapter.setOnItemClickListener(object : VehicleInventorylistAdapter.OnItemClickListeners {
            override fun onItemLongClick(binding: VehicleinventorylistItemBinding, vehicleInventory: VehicleInventory, position: Int) {
                val builder = AlertDialog.Builder(this@VehicleInventorylistActivity)
                builder.setTitle("삭제 확인")
                builder.setMessage("이 아이템을 삭제하시겠습니까?")

                builder.setPositiveButton("삭제") { dialog, which ->
                    vehicleInventorylistAdapter.removeItem(position)
                    val vehicleInventoryId = vehicleInventory.vehicleInventoryId?: error("Product ID가 null입니다.")
                    deleteVehicleInventory(vehicleInventoryId)
                }

                builder.setNegativeButton("취소") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        })

        binding.inventoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.inventoryRecyclerView.adapter = vehicleInventorylistAdapter

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddVehicleInventoryActivity::class.java)
            intent.putExtra("intentUser", intentUser)
            startActivity(intent)
        }

        binding.exitButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("intentUser", intentUser)
            startActivity(intent)
            finish()
        }
    }

    private fun getVehicleInventoriesByUserId(userId: Int) {
        val call = RetrofitService.vehicleInventoryService.getVehicleInventoriesByUserId(userId)
        call.enqueue(object : Callback<List<VehicleInventory>> {
            override fun onResponse(call: Call<List<VehicleInventory>>, response: Response<List<VehicleInventory>>) {
                if (response.isSuccessful) {
                    val vehicleInventory = response.body()
                    if (vehicleInventory != null) {
                        inverntorytLists.addAll(vehicleInventory)
                        Log.d("VehicleInventorylistActivity", "Products added to list: $vehicleInventory")
                        vehicleInventorylistAdapter.notifyDataSetChanged()
                    } else {
                        Log.e("VehicleInventorylistActivity", "No order data found in response")
                    }
                } else {
                    Log.e("VehicleInventorylistActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<VehicleInventory>>, t: Throwable) {
                Log.e("VehicleInventorylistActivity", "Network request failed", t)
            }
        })
    }

    private fun deleteVehicleInventory(vehicleInventoryId: Int) {
        val call = RetrofitService.vehicleInventoryService.deleteVehicleInventory(vehicleInventoryId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("VehicleInventoryActivity", "서버에서 차량 인벤토리 아이템 삭제 성공")
                    vehicleInventorylistAdapter.notifyDataSetChanged()
                } else {
                    Log.e("VehicleInventoryActivity", "서버에서 차량 인벤토리 아이템 삭제 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("VehicleInventoryActivity", "서버와의 통신 실패", t)
            }
        })
    }
}

