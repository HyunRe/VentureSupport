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

    // ViewBinding을 초기화합니다.
    private val binding: CreateorderBinding by lazy {
        CreateorderBinding.inflate(layoutInflater)
    }

    // 어댑터와 데이터 목록을 초기화합니다.
    private lateinit var createOrderAdapter: CreateOrderAdapter
    private var productLists = ArrayList<Product>()
    private var user: User? = null
    private var company: Company? = null
    private var product: Product? = null
    private lateinit var order: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 인텐트로 전달된 데이터를 받습니다.
        @Suppress("DEPRECATION")
        user = intent.getParcelableExtra("intentUser")
        @Suppress("DEPRECATION")
        company = intent.getParcelableExtra("intentCompany")
        @Suppress("DEPRECATION")
        product = intent.getParcelableExtra("intentProduct")

        // 주문 제출 버튼 클릭 리스너
        binding.submitButton.setOnClickListener {
            // 사용자 입력값을 가져옵니다.
            val date = binding.date.text.toString() // 주문 날짜를 문자열로 가져옵니다.
            val SupplierName = binding.SupplierName.text.toString() // 공급자의 이름을 문자열로 가져옵니다.
            val SupplierPhoneNumber = binding.SupplierPhoneNumber.text.toString() // 공급자의 전화번호를 문자열로 가져옵니다.
            val SupplierLocation = binding.SupplierLocation.text.toString() // 공급자의 위치를 문자열로 가져옵니다.
            val Salary = binding.Salary.text.toString() // 급여를 문자열로 가져옵니다.
            val TotalAmount = binding.TotalAmount.text.toString() // 총 금액을 문자열로 가져옵니다.

            // 공급자 객체를 생성합니다.
            val supplier = Supplier(
                supplierId = null, // 새 공급자이므로 ID는 null입니다.
                supplierName = SupplierName, // 공급자 이름 설정
                supplierPhoneNumber = SupplierPhoneNumber, // 공급자 전화번호 설정
                supplierLocation = SupplierLocation, // 공급자 위치 설정
            )

            // 주문 객체를 생성합니다.
            order = Order(
                orderId = null, // 새 주문이므로 ID는 null입니다.
                product = product!!, // 선택한 제품을 설정합니다.
                supplier = supplier, // 공급자 설정
                company = company!!, // 회사 설정
                user = null, // 사용자 정보는 나중에 설정합니다.
                date = Date(date.toLong()), // 주문 날짜 설정 (Unix timestamp를 Date 객체로 변환)
                salary = Salary.toDouble(), // 급여를 Double로 변환
                totalAmount = TotalAmount.toInt() // 총 금액을 Integer로 변환
            )

            // 공급자 정보를 서버에 저장합니다.
            createSupplier(supplier)

            // MyOrderlistActivity로 이동합니다.
            val intent = Intent(this, MyOrderlistActivity::class.java)
            intent.putExtra("intentCompany", company)
            startActivity(intent)
            finish()
        }

        // 제품 추가 버튼 클릭 리스너
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        // 어댑터 초기화 및 설정
        createOrderAdapter = CreateOrderAdapter(productLists)
        createOrderAdapter.setOnItemClickListener(object : CreateOrderAdapter.OnItemClickListeners {
            override fun onItemLongClick(binding: CreateorderItemBinding, product: Product, position: Int) {
                // 제품 삭제 확인 대화상자 생성
                val builder = AlertDialog.Builder(this@CreateOrderActivity)
                builder.setTitle("삭제 확인")
                builder.setMessage("이 아이템을 삭제하시겠습니까?")

                // 삭제 버튼 클릭 시 실행할 코드
                builder.setPositiveButton("삭제") { dialog, which ->
                    createOrderAdapter.removeItem(position) // 어댑터에서 해당 아이템을 삭제합니다.
                    val productId = product.productId ?: error("Product ID가 null입니다.") // 제품 ID가 null일 경우 에러를 발생시킵니다.
                    deleteProduct(productId) // 제품 삭제 API 호출
                }

                // 취소 버튼 클릭 시 대화상자를 닫습니다.
                builder.setNegativeButton("취소") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        })

        // 리사이클러뷰 설정
        binding.productRecyclerView.layoutManager = LinearLayoutManager(this) // 리사이클러뷰를 세로 방향의 리스트 형태로 설정합니다.
        binding.productRecyclerView.adapter = createOrderAdapter // 어댑터를 리사이클러뷰에 연결합니다.
        createOrderAdapter.notifyDataSetChanged() // 어댑터의 데이터가 변경되었음을 알립니다.
    }

    // 공급자 정보를 서버에 저장하는 함수
    private fun createSupplier(supplier: Supplier) {
        val call = RetrofitService.supplierSevice.createSupplier(supplier)
        call.enqueue(object : Callback<Supplier> {
            override fun onResponse(call: Call<Supplier>, response: Response<Supplier>) {
                if (response.isSuccessful) {
                    val createdSupplier = response.body()
                    println("Warehouse 생성 성공: $createdSupplier")
                    createOrder(order) // 주문 생성 함수 호출
                } else {
                    println("Warehouse 생성 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Supplier>, t: Throwable) {
                println("네트워크 요청 실패: ${t.message}")
            }
        })
    }

    // 주문 정보를 서버에 저장하는 함수
    private fun createOrder(order: Order) {
        val call = RetrofitService.orderService.createOrder(order)
        call.enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val createdOrder = response.body()
                    println("Order 생성 성공: $createdOrder")
                } else {
                    println("Order 생성 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                println("네트워크 요청 실패: ${t.message}")
            }
        })
    }

    // 제품을 삭제하는 함수
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
