package com.example.test2

import Product
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.MyorderlistBinding
import com.example.test2.databinding.MyorderlistItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOrderlistActivity: AppCompatActivity() {
    private val binding: MyorderlistBinding by lazy {
        MyorderlistBinding.inflate(layoutInflater)
    }
    private lateinit var myOrderlistAdapter: MyOrderlistAdapter
    private var myOrderLists = ArrayList<Order>()
    private var productLists = ArrayList<Product>()
    private var intentCompany: Company? = null
    private var intentOrder: Order? = null
    private var intentSupplier: Supplier? = null
    private var updatedOrder: Order? = null
    private var updatedSupplier: Supplier? = null
    private var productInformations = ArrayList<ProductInformation>()

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentCompany = intent.getParcelableExtra("intentCompany")
        intentSupplier = intent.getParcelableExtra("intentSupplier")
        intentOrder = intent.getParcelableExtra("intentOrder")
        updatedSupplier = intent.getParcelableExtra("updatedSupplier")
        updatedOrder = intent.getParcelableExtra("updatedOrder")
        intent?.let { intent ->
            productLists = intent.getParcelableArrayListExtra<Product>("productLists") ?: ArrayList()
        }

        getOrdersByCompanyId(intentCompany?.companyId!!)

        myOrderlistAdapter = MyOrderlistAdapter(myOrderLists)
        binding.myorderlistRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.myorderlistRecyclerView.adapter = myOrderlistAdapter

        binding.exitButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        CoroutineScope(Dispatchers.Main).launch {
            // 모든 비동기 작업이 완료될 때까지 대기합니다
            val productDeferred = async(Dispatchers.Default) { createProductsAsync() }

            // 결과를 가져옵니다
            productDeferred.await()

            // 이제 모든 비동기 작업이 완료되었으므로 추가 작업을 진행합니다
            intentOrder?.let { nonNullOrder ->
                productLists.forEach { product ->
                    val productInformation = ProductInformation(
                        productInformationId = null,
                        order = nonNullOrder,
                        product = product
                    )
                    productInformations.add(productInformation)
                }
            }

            // 생성
            intentSupplier?.let { createSupplier(it) }
            // 수정
            updatedSupplier?.let { updateSupplier(it) }

            binding.floatingActionButton.setOnClickListener {
                val intent = Intent(this@MyOrderlistActivity, CreateOrderActivity::class.java)
                intent.putExtra("intentCompany", intentCompany)
                startActivity(intent)
            }

            myOrderlistAdapter.setOnItemClickListener(object : MyOrderlistAdapter.OnItemClickListeners {
                override fun onItemClick(binding: MyorderlistItemBinding, order: Order, position: Int) {
                    val intent = Intent(this@MyOrderlistActivity, EditOrderActivity::class.java).apply {
                        putExtra("editOrder", myOrderLists[position])
                        putExtra("intentCompany", intentCompany)
                        putExtra("productLists", productLists)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }

                override fun onItemLongClick(binding: MyorderlistItemBinding, order: Order, position: Int) {
                    val builder = AlertDialog.Builder(this@MyOrderlistActivity)
                    builder.setTitle("삭제 확인")
                    builder.setMessage("이 아이템을 삭제하시겠습니까?")

                    builder.setPositiveButton("삭제") { dialog, which ->
                        myOrderlistAdapter.removeItem(position)
                        val orderId = order.orderId ?: error("Order ID가 null입니다.")
                        deleteProductInformationByOrderId(orderId)
                    }

                    builder.setNegativeButton("취소") { dialog, which ->
                        dialog.dismiss()
                    }

                    val dialog = builder.create()
                    dialog.show()
                }
            })
        }
    }

    // 비동기적으로 제품을 생성하는 함수
    private suspend fun createProductsAsync() {
        withContext(Dispatchers.Default) {
            productLists?.let { nonNullProductLists ->
                for (item in nonNullProductLists) {
                    createProduct(item)
                }
            }
        }
    }

    private fun createProduct(product: Product) {
        val call = RetrofitService.productService.createProduct(product)
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val createdProduct = response.body()
                    productInformations.find { it.product == product }?.product = createdProduct
                    println("Product 생성 성공: $createdProduct")
                } else {
                    println("Product 생성 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                println("네트워크 요청 실패: ${t.message}")
            }
        })
    }

    private fun createSupplier(supplier: Supplier) {
        val call = RetrofitService.supplierService.createSupplier(supplier)
        call.enqueue(object : Callback<Supplier> {
            override fun onResponse(call: Call<Supplier>, response: Response<Supplier>) {
                if (response.isSuccessful) {
                    val createdSupplier = response.body()
                    println("Supplier 생성 성공: $createdSupplier")
                    intentOrder?.supplier = createdSupplier!!
                    createOrder(intentOrder!!)
                    Log.d("CreateOrderActivity", "Order Created: $intentOrder")
                } else {
                    println("Supplier 생성 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Supplier>, t: Throwable) {
                println("네트워크 요청 실패: ${t.message}")
            }
        })
    }

    private fun createOrder(order: Order) {
        val call = RetrofitService.orderService.createOrder(order)
        call.enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val createdOrder = response.body()
                    productInformations.forEach { it.order = createdOrder }
                    println("Order 생성 성공: $createdOrder")
                    productInformations.forEach { productInformation ->
                        createProductInformation(productInformation)
                    }
                    createdOrder?.let {
                        myOrderLists.add(it)
                        myOrderlistAdapter.notifyItemInserted(myOrderLists.size - 1)
                    }
                } else {
                    println("Order 생성 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                println("네트워크 요청 실패: ${t.message}")
            }
        })
    }

    private fun getOrdersByCompanyId(companyId: Int) {
        val call = RetrofitService.orderService.getOrdersByCompanyId(companyId)
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val order = response.body()
                    if (order != null) {
                        myOrderlistAdapter.addOrders(order)
                        // 내 오더 개수
                        binding.count.text = myOrderLists.size.toString()
                        Log.d("MyOrderlistActivity", "Order added to list: $order")
                    } else {
                        Log.e("MyOrderlistActivity", "No order data found in response")
                    }
                } else {
                    Log.e("MyOrderlistActivity", "Request failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("MyOrderlistActivity", "Network request failed", t)
            }
        })
    }

    private fun deleteProductInformationByOrderId(orderId: Int) {
        val call = RetrofitService.productInformationService.deleteProductInformationByOrderId(orderId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("MyOrderlistActivity", "서버에서 제품 정보 삭제 성공")
                    deleteOrder(orderId)
                } else {
                    Log.e("MyOrderlistActivity", "서버에서 제품 정보 삭제 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("MyOrderlistActivity", "서버와의 통신 실패", t)
            }
        })
    }

    private fun deleteOrder(orderId: Int) {
        val call = RetrofitService.orderService.deleteOrder(orderId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("MyOrderlistActivity", "서버에서 아이템 삭제 성공")
                } else {
                    Log.e("MyOrderlistActivity", "서버에서 아이템 삭제 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("MyOrderlistActivity", "서버와의 통신 실패", t)
            }
        })
    }

    private fun createProductInformation(productInformation: ProductInformation) {
        val call = RetrofitService.productInformationService.createProductInformation(productInformation)
        call.enqueue(object : Callback<ProductInformation> {
            override fun onResponse(call: Call<ProductInformation>, response: Response<ProductInformation>) {
                if (response.isSuccessful) {
                    val createdProductInformation = response.body()
                    println("ProductInformation 생성 성공: $createdProductInformation")
                } else {
                    println("ProductInformation 생성 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProductInformation>, t: Throwable) {
                println("네트워크 요청 실패: ${t.message}")
            }
        })
    }

    private fun updateSupplier(supplier: Supplier) {
        val call = RetrofitService.supplierService.updateSupplier(supplier.supplierId!!, supplier)
        call.enqueue(object : Callback<Supplier> {
            override fun onResponse(call: Call<Supplier>, response: Response<Supplier>) {
                if (response.isSuccessful) {
                    val updatedSupplier = response.body()
                    if (updatedSupplier != null) {
                        // 업데이트된 공급업체 데이터를 처리함
                        Log.d("EditSupplierActivity", "Supplier updated successfully: $updatedSupplier")
                        updatedOrder?.supplier = updatedSupplier!!
                        updateOrder(updatedOrder!!)
                        // RecyclerView에 변경된 데이터를 반영합니다.
                        updateRecyclerView(updatedOrder!!)
                    } else {
                        Log.e("EditSupplierActivity", "Failed to update supplier: No response body")
                    }
                } else {
                    Log.e("EditSupplierActivity", "Failed to update supplier: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Supplier>, t: Throwable) {
                Log.e("EditSupplierActivity", "Network request failed", t)
            }
        })
    }

    private fun updateOrder(order: Order) {
        val call = RetrofitService.orderService.updateOrder(updatedOrder?.orderId!!, order)
        call.enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val updatedOrder = response.body()
                    if (updatedOrder != null) {
                        // 업데이트된 주문 데이터를 처리함
                        Log.d("EditOrderActivity", "Order updated successfully: $updatedOrder")
                    } else {
                        Log.e("EditOrderActivity", "Failed to update order: No response body")
                    }
                } else {
                    Log.e("EditOrderActivity", "Failed to update order: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Log.e("EditOrderActivity", "Network request failed", t)
            }
        })
    }

    // 업데이트된 Order를 RecyclerView에 반영합니다.
    private fun updateRecyclerView(updatedOrder: Order) {
        // RecyclerView의 각 아이템을 순회하면서 업데이트된 Order와 일치하는 아이템을 찾습니다.
        for (i in myOrderLists.indices) {
            if (myOrderLists[i].orderId == updatedOrder.orderId) {
                // 일치하는 아이템을 업데이트합니다.
                myOrderLists[i] = updatedOrder
                // 해당 위치의 아이템을 업데이트함을 Adapter에 알려줍니다.
                myOrderlistAdapter.notifyItemChanged(i)
                break
            }
        }
    }
}