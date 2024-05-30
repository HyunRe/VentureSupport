package com.example.venturesupport

import User
import Warehouse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.venturesupport.databinding.WarehouseBinding
import com.example.venturesupport.databinding.WarehouseItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WarehouseActivity: AppCompatActivity() {
    private val binding: WarehouseBinding by lazy {
        WarehouseBinding.inflate(layoutInflater)
    }
    private lateinit var warehouseAdapter: WarehouseAdapter
    private var warehouseLists = ArrayList<Warehouse>()
    private var user: User? = null
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        user = intent.getParcelableExtra("user")
        @Suppress("DEPRECATION")
        currentUser = intent.getParcelableExtra("currentUser")

        val id = user?.userId ?: currentUser?.userId ?: 0
        getWarehousesByUserId(id)

        // 창고 생성
        binding.addWarehouseButton.setOnClickListener {
            val intent = Intent(this@WarehouseActivity, WarehouseRegistrationActivity::class.java).apply {
                putExtra("user", user)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        // 창고 조회
        warehouseAdapter = WarehouseAdapter(warehouseLists)
        warehouseAdapter.setOnItemClickListener(object : WarehouseAdapter.OnItemClickListeners {
            override fun onItemLongClick(binding: WarehouseItemBinding, warehouse: Warehouse, position: Int) {
                val builder = AlertDialog.Builder(this@WarehouseActivity)
                builder.setTitle("삭제 확인")
                builder.setMessage("이 아이템을 삭제하시겠습니까?")

                builder.setPositiveButton("삭제") { dialog, which ->
                    warehouseAdapter.removeItem(position)
                    val warehouseId = warehouse.warehouseId?: error("Warehouse ID가 null입니다.")
                    deleteWarehouse(warehouseId)
                }

                builder.setNegativeButton("취소") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        })

        binding.warehouseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.warehouseRecyclerView.adapter = warehouseAdapter
        warehouseAdapter.notifyDataSetChanged()
    }

    private fun getWarehousesByUserId(userId: Int) {
        val call = RetrofitService.warehouseService.getWarehousesByUserId(userId)
        call.enqueue(object : Callback<List<Warehouse>> {
            override fun onResponse(call: Call<List<Warehouse>>, response: Response<List<Warehouse>>) {
                if (response.isSuccessful) {
                    val warehouses = response.body()
                    if (warehouses != null) {
                        warehouseLists.addAll(warehouses)
                        warehouseAdapter.updateData(warehouseLists)
                    } else {
                        Log.e("WarehouseActivity", "No warehouse data found in response")
                    }
                } else {
                    Log.e("WarehouseActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Warehouse>>, t: Throwable) {
                Log.e("WarehouseActivity", "Network request failed", t)
            }
        })
    }

    private fun deleteWarehouse(warehouseId: Int) {
        val call = RetrofitService.warehouseService.deleteWarehouse(warehouseId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("WarehouseActivity", "서버에서 아이템 삭제 성공")
                } else {
                    Log.e("WarehouseActivity", "서버에서 아이템 삭제 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("WarehouseActivity", "서버와의 통신 실패", t)
            }
        })
    }
}