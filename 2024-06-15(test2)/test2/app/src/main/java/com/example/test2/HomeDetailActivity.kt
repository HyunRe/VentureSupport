package com.example.test2

import Product
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.HomedetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeDetailActivity : AppCompatActivity() {
    private val binding: HomedetailBinding by lazy {
        HomedetailBinding.inflate(layoutInflater)
    }
    private lateinit var homeDetailAdapter: HomeDetailAdapter
    private var productOrderLists = ArrayList<Product>()
    private var intentOrder: Order? = null
    private var intentUser: User? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentOrder = intent.getParcelableExtra("intentOrder")
        intentUser = intent.getParcelableExtra("intentUser")

        binding.date.text = intentOrder?.date.toString()
        binding.SupplierName.text = intentOrder?.supplier?.supplierName
        binding.SupplierPhoneNumber.text = intentOrder?.supplier?.supplierPhoneNumber
        binding.SupplierLocation.text = intentOrder?.supplier?.supplierLocation
        binding.Salary.text = intentOrder?.salary.toString()
        binding.TotalAmount.text = intentOrder?.totalAmount.toString()

        getProductInformationByOrderId(intentOrder?.orderId!!)

        homeDetailAdapter = HomeDetailAdapter(productOrderLists)
        binding.productRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productRecyclerView.adapter = homeDetailAdapter

        // 전달 값 수정
        binding.acceptButton.setOnClickListener {
            showAcceptDialog()
        }

        // 전달 값 수정
        binding.cancelButton.setOnClickListener {
            Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("intentUser", intentUser)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun showAcceptDialog() {
        AlertDialog.Builder(this)
            .setTitle("수락 확인")
            .setMessage("수락하시겠습니까?")
            .setPositiveButton("예") { dialog, _ ->
                if (intentOrder?.user == null) {
                    intentOrder?.user = intentUser
                    updateOrder(intentOrder!!)
                }
                Toast.makeText(this, "수락 완료", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("intentUser", intentUser)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                dialog.dismiss()
            }
            .setNegativeButton("아니오") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun updateOrder(order: Order) {
        val call = RetrofitService.orderService.updateOrder(intentOrder?.orderId!!, order)
        call.enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val updatedOrder = response.body()
                    if (updatedOrder != null) {
                        // Handle the updated order object
                        Log.d("HomeDetailActivity", "Order updated successfully: $updatedOrder")
                    } else {
                        Log.e("HomeDetailActivity", "Failed to update order: No response body")
                    }
                } else {
                    Log.e("HomeDetailActivity", "Failed to update order: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Log.e("HomeDetailActivity", "Network request failed", t)
            }
        })
    }

    private fun getProductInformationByOrderId(orderId: Int) {
        val call = RetrofitService.productInformationService.getProductInformationByOrderId(orderId)
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val products = response.body()
                    if (products != null) {
                        // 제품 정보를 어댑터에 추가하거나 필요한 작업을 수행합니다.
                        productOrderLists.addAll(products)
                        // 로그 출력
                        Log.d("HomeDetailActivity", "Product Information added to list: $productOrderLists")
                        homeDetailAdapter.notifyDataSetChanged()
                    } else {
                        Log.e("HomeDetailActivity", "No product information data found in response")
                    }
                } else {
                    Log.e("HomeDetailActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("HomeDetailActivity", "Network request failed", t)
            }
        })
    }
}