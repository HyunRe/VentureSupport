package com.example.venturesupport

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

class WarehouseActivity : AppCompatActivity() {
    private val binding: WarehouseBinding by lazy {
        WarehouseBinding.inflate(layoutInflater)
    }
    private lateinit var warehouseAdapter: WarehouseAdapter
    private var warehouseLists = ArrayList<Warehouse>()
    private var intentUser: User? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentUser = intent.getParcelableExtra("intentUser")

        getWarehousesByUserId(intentUser?.userId!!)

        binding.addWarehouseButton.setOnClickListener {
            val intent = Intent(this, WarehouseRegistrationActivity::class.java).apply {
                putExtra("intentUser", intentUser)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        warehouseAdapter = WarehouseAdapter(warehouseLists)
        warehouseAdapter.setOnItemClickListener(object : WarehouseAdapter.OnItemClickListeners {
            override fun onItemLongClick(binding: WarehouseItemBinding, warehouse: Warehouse, position: Int) {
                // 삭제 확인 다이얼로그 표시
                val builder = AlertDialog.Builder(this@WarehouseActivity)
                builder.setTitle("삭제 확인")
                builder.setMessage("이 아이템을 삭제하시겠습니까?")

                builder.setPositiveButton("삭제") { dialog, which ->
                    // 아이템 삭제
                    warehouseAdapter.removeItem(position)
                    val warehouseId = warehouse.warehouseId ?: error("Warehouse ID가 null입니다.")
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

        // 수정 필요
        binding.exitButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("intentUser", intentUser)
            startActivity(intent)
            finish()
        }
    }

    private fun getWarehousesByUserId(userId: Int) {
        val call = RetrofitService.warehouseService.getWarehousesByUserId(userId)
        call.enqueue(object : Callback<List<Warehouse>> {
            override fun onResponse(call: Call<List<Warehouse>>, response: Response<List<Warehouse>>) {
                if (response.isSuccessful) {
                    val warehouses = response.body()
                    if (warehouses != null) {
                        Log.d("WarehouseActivity", "Warehouse added to list: $warehouses")
                        warehouseLists.addAll(warehouses)
                        warehouseAdapter.updateData(warehouseLists)
                        warehouseAdapter.notifyDataSetChanged()
                    } else {
                        Log.e("WarehouseActivity", "응답에서 창고 데이터를 찾을 수 없습니다.")
                    }
                } else {
                    Log.e("WarehouseActivity", "요청이 실패했습니다. 상태 코드: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Warehouse>>, t: Throwable) {
                Log.e("WarehouseActivity", "네트워크 요청 실패", t)
            }
        })
    }

    private fun deleteWarehouse(warehouseId: Int) {
        val call = RetrofitService.warehouseService.deleteWarehouse(warehouseId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("WarehouseActivity", "서버에서 아이템 삭제 성공")
                    warehouseAdapter.updateData(warehouseLists)
                    warehouseAdapter.notifyDataSetChanged()
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
