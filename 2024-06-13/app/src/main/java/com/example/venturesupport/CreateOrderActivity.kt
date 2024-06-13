package com.example.venturesupport

import User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.venturesupport.databinding.CreateorderBinding
import com.example.venturesupport.databinding.CreateorderItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

class CreateOrderActivity: AppCompatActivity() {
    private val binding: CreateorderBinding by lazy {
        CreateorderBinding.inflate(layoutInflater)
    }
    private lateinit var createOrderAdapter: CreateOrderAdapter
    private var productLists = ArrayList<Product>()
    private var user: User? = null
    private var company: Company? = null
    private var product: Product? = null
    private lateinit var order: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        user = intent.getParcelableExtra("intentUser")
        @Suppress("DEPRECATION")
        company = intent.getParcelableExtra("intentCompany")
        @Suppress("DEPRECATION")
        product = intent.getParcelableExtra("intentProduct")

        binding.submitButton.setOnClickListener {
            val date = binding.date.text.toString()
            val SupplierName = binding.SupplierName.text.toString()
            val SupplierPhoneNumber = binding.SupplierPhoneNumber.text.toString()
            val SupplierLocation = binding.SupplierLocation.text.toString()
            val Salary = binding.Salary.text.toString()
            val TotalAmount = binding.TotalAmount.text.toString()

            val supplier = Supplier (
                supplierId = null,
                supplierName = SupplierName,
                supplierPhoneNumber = SupplierPhoneNumber,
                supplierLocation = SupplierLocation,
            )

            order = Order(
                orderId = null,
                product = product!!,
                supplier = supplier,
                company = company!!,
                user = null,
                date = Date(date.toLong()),
                salary = Salary.toDouble(),
                totalAmount = TotalAmount.toInt()
            )

            createSupplier(supplier)
            val intent = Intent(this, MyOrderlistActivity::class.java)
            intent.putExtra("intentCompany", company)
            startActivity(intent)
            finish()
        }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        createOrderAdapter = CreateOrderAdapter(productLists)
        createOrderAdapter.setOnItemClickListener(object : CreateOrderAdapter.OnItemClickListeners {
            override fun onItemLongClick(binding: CreateorderItemBinding, product: Product, position: Int) {
                val builder = AlertDialog.Builder(this@CreateOrderActivity)
                builder.setTitle("삭제 확인")
                builder.setMessage("이 아이템을 삭제하시겠습니까?")

                builder.setPositiveButton("삭제") { dialog, which ->
                    createOrderAdapter.removeItem(position)
                    val productId = product.productId?: error("Product ID가 null입니다.")
                    deleteProduct(productId)
                }

                builder.setNegativeButton("취소") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        })

        binding.productRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productRecyclerView.adapter = createOrderAdapter
        createOrderAdapter.notifyDataSetChanged()
    }

    private fun createSupplier(supplier: Supplier) {
        val call = RetrofitService.supplierSevice.createSupplier(supplier)
        call.enqueue(object : Callback<Supplier> {
            override fun onResponse(call: Call<Supplier>, response: Response<Supplier>) {
                if (response.isSuccessful) {
                    val createdSupplier = response.body()
                    println("Warehouse 생성 성공: $createdSupplier")
                    createOrder(order)
                } else {
                    println("Warehouse 생성 실패: ${response.code()}")
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
                    println("Warehouse 생성 성공: $createdOrder")
                } else {
                    println("Warehouse 생성 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                println("네트워크 요청 실패: ${t.message}")
            }
        })
    }

    private fun deleteProduct(productId: Int) {
        val call = RetrofitService.productService.deleteProduct(productId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("CreateOrderActivity", "서버에서 아이템 삭제 성공")
                } else {
                    Log.e("CreateOrderActivity", "서버에서 아이템 삭제 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("CreateOrderActivity", "서버와의 통신 실패", t)
            }
        })
    }
}