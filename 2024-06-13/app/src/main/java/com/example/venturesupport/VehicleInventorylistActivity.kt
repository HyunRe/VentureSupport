package com.example.venturesupport

import User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.venturesupport.databinding.VehicleinventorylistBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleInventorylistActivity : AppCompatActivity() {
    private val binding: VehicleinventorylistBinding by lazy {
        VehicleinventorylistBinding.inflate(layoutInflater)
    }
    private lateinit var vehicleInventorylistAdapter: VehicleInventorylistAdapter
    private var productLists = ArrayList<Product>()
    private var intentOrder: Order? = null
    private var intentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Intent로부터 User와 Order 객체를 받아옵니다.
        @Suppress("DEPRECATION")
        intentUser = intent.getParcelableExtra("intentUser")
        @Suppress("DEPRECATION")
        intentOrder = intent.getParcelableExtra("intentOrder")

        // User ID를 사용하여 Vehicle Inventory 데이터를 가져옵니다.
        getVehicleInventoryByUserId(intentUser?.userId!!)

        // RecyclerView 및 어댑터를 초기화합니다.
        // 어댑터 초기화 시 롱클릭 콜백 추가
        vehicleInventorylistAdapter = VehicleInventorylistAdapter(productLists) { product ->
            // 롱클릭 시 다이얼로그를 띄워서 확인 후 삭제
            showDeleteConfirmationDialog(product)
        }
        binding.inventoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.inventoryRecyclerView.adapter = vehicleInventorylistAdapter
        vehicleInventorylistAdapter.notifyDataSetChanged()

        // Floating Action Button 클릭 시 AddVehicleInventoryActivity로 전환합니다.
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddVehicleInventoryActivity::class.java)
            intent.putExtra("intentUser", intentUser)
            intent.putExtra("intentOrder", intentOrder)
            startActivity(intent)
        }
    }



    // User ID를 사용하여 Vehicle Inventory 데이터를 가져오는 함수입니다.
    private fun getVehicleInventoryByUserId(userId: Int) {
        // Retrofit을 사용하여 서버로부터 Vehicle Inventory 데이터를 가져옵니다.
        val call = RetrofitService.vehicleInventoryService.getVehicleInventoriesByUserId(userId)
        call.enqueue(object : Callback<List<VehicleInventory>> {
            override fun onResponse(call: Call<List<VehicleInventory>>, response: Response<List<VehicleInventory>>) {
                if (response.isSuccessful) {
                    val vehicleInventories = response.body()
                    if (vehicleInventories != null) {
                        // 서버로부터 받은 Vehicle Inventory 데이터를 productLists에 추가합니다.
                        for (inventory in vehicleInventories) {
                            val product = inventory.product
                            if (product != null) {
                                productLists.add(product)
                                Log.d("VehicleInventoryListActivity", "Products added to list: $product")
                            } else {
                                Log.e("VehicleInventoryListActivity", "No products found in vehicle inventory: $inventory")
                            }
                        }
                        // 어댑터에 데이터 변경을 알립니다.
                        vehicleInventorylistAdapter.notifyDataSetChanged()
                    } else {
                        Log.e("VehicleInventoryListActivity", "No vehicle inventory data found in response")
                    }
                } else {
                    Log.e("VehicleInventoryListActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<VehicleInventory>>, t: Throwable) {
                Log.e("VehicleInventoryListActivity", "Network request failed", t)
            }
        })
    }

    private fun showDeleteConfirmationDialog(product: Product) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("삭제 확인")
        builder.setMessage("정말로 이 항목을 삭제하시겠습니까?")
        builder.setPositiveButton("삭제") { dialog, _ ->
            vehicleInventorylistAdapter.removeItem(product)
            dialog.dismiss()
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

}
