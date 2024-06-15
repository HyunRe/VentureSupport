package com.example.test2

import Product
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.CreateorderBinding
import com.example.test2.databinding.CreateorderItemBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CreateOrderActivity: AppCompatActivity() {
    private val binding: CreateorderBinding by lazy {
        CreateorderBinding.inflate(layoutInflater)
    }
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var createOrderAdapter: CreateOrderAdapter
    private var productLists: ArrayList<Product> = ArrayList()
    private var intentCompany: Company? = null
    private var order: Order? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intentCompany = intent.getParcelableExtra("intentCompany")

        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        // SharedPreferences에서 productLists 값을 불러오기
        val productListsJson = sharedPreferences.getString("productLists", null)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Product>>() {}.type
        productLists = if (productListsJson != null) {
            gson.fromJson(productListsJson, type)
        } else {
            ArrayList()
        }

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
                supplier = supplier,
                company = intentCompany!!,
                user = null,
                date = date,
                salary = Salary.toInt(),
                totalAmount = TotalAmount.toInt()
            )

            Handler().postDelayed({
                val intent = Intent(this, MyOrderlistActivity::class.java)
                intent.putExtra("intentSupplier", supplier)
                intent.putExtra("intentOrder", order)
                intent.putExtra("productLists", productLists)
                intent.putExtra("intentCompany", intentCompany)
                startActivity(intent)
            }, 1000)

            Handler().postDelayed({
                clearProductLists()
                finish()
            }, 1500)
        }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            intent.putExtra("intentCompany", intentCompany)
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
                    productLists.remove(product) // 해당 아이템을 productLists에서 삭제
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
    }

    // productLists를 초기화하는 메서드
    private fun clearProductLists() {
        productLists.clear()
        val editor = sharedPreferences.edit()
        editor.remove("productLists")
        editor.apply()
        (binding.productRecyclerView.adapter as? CreateOrderAdapter)?.updateProductList(productLists)
    }
}