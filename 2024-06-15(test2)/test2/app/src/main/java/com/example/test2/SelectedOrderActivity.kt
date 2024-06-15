package com.example.test2

import Product
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.SelectedorderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectedOrderActivity: AppCompatActivity() {
    private val binding: SelectedorderBinding by lazy {
        SelectedorderBinding.inflate(layoutInflater)
    }
    private lateinit var selectedOrderAdapter: SelectedOrderAdapter
    private var productOrderLists = ArrayList<Product>()
    private var selectedOrder: Order? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        selectedOrder = intent.getParcelableExtra("selectedOrder")

        binding.date.text = selectedOrder?.date.toString()
        binding.SupplierName.text = selectedOrder?.supplier?.supplierName
        binding.SupplierPhoneNumber.text = selectedOrder?.supplier?.supplierPhoneNumber
        binding.SupplierLocation.text = selectedOrder?.supplier?.supplierLocation
        binding.Salary.text = selectedOrder?.salary.toString()
        binding.TotalAmount.text = selectedOrder?.totalAmount.toString()

        getProductInformationByOrderId(selectedOrder?.orderId!!)

        selectedOrderAdapter = SelectedOrderAdapter(productOrderLists)
        binding.productRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productRecyclerView.adapter = selectedOrderAdapter
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
                        selectedOrderAdapter.notifyDataSetChanged()
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