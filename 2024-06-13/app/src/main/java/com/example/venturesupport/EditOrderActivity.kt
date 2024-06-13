package com.example.venturesupport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.EditorderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

/**
 * 주문 수정 화면을 위한 액티비티 클래스입니다.
 */
class EditOrderActivity : AppCompatActivity() {
    // 바인딩 변수: 레이아웃과 연결하여 UI 요소에 접근할 수 있도록 합니다.
    private val binding: EditorderBinding by lazy {
        EditorderBinding.inflate(layoutInflater)
    }
    private var editOrder: Order? = null
    private lateinit var date: String
    private lateinit var supplier_Name: String
    private lateinit var supplier_PhoneNumber: String
    private lateinit var supplier_Location: String
    private lateinit var salary: String
    private lateinit var totalAmount: String

    /**
     * 액티비티가 생성될 때 호출됩니다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 인텐트를 통해 전달된 주문 정보를 가져옵니다.
        @Suppress("DEPRECATION")
        editOrder = intent.getParcelableExtra("editOrder")

        // 기존 주문 정보로 뷰를 초기화합니다.
        binding.dateTextView.setText(editOrder?.date.toString(), TextView.BufferType.EDITABLE)
        binding.SupplierNameTextView.setText(editOrder?.supplier?.supplierName, TextView.BufferType.EDITABLE)
        binding.SupplierPhoneNumberTextView.setText(editOrder?.supplier?.supplierPhoneNumber, TextView.BufferType.EDITABLE)
        binding.SupplierLocationTextView.setText(editOrder?.supplier?.supplierLocation, TextView.BufferType.EDITABLE)
        binding.SalaryTextView.setText(editOrder?.salary.toString(), TextView.BufferType.EDITABLE)
        binding.TotalAmountTextView.setText(editOrder?.totalAmount.toString(), TextView.BufferType.EDITABLE)

        // 수정 버튼 클릭 리스너 설정
        binding.updateButton.setOnClickListener {
            // 입력된 값을 가져와서 변수에 저장합니다.
            date = if (binding.date.text.isNullOrEmpty()) {
                editOrder?.date.toString()
            } else {
                binding.date.text.toString()
            }
            supplier_Name = if (binding.SupplierName.text.isNullOrEmpty()) {
                editOrder?.supplier?.supplierName.toString()
            } else {
                binding.SupplierName.text.toString()
            }
            supplier_PhoneNumber = if (binding.SupplierPhoneNumber.text.isNullOrEmpty()) {
                editOrder?.supplier?.supplierPhoneNumber.toString()
            } else {
                binding.SupplierPhoneNumber.text.toString()
            }
            supplier_Location = if (binding.SupplierLocation.text.isNullOrEmpty()) {
                editOrder?.supplier?.supplierLocation.toString()
            } else {
                binding.SupplierLocation.text.toString()
            }
            salary = if (binding.Salary.text.isNullOrEmpty()) {
                editOrder?.salary.toString()
            } else {
                binding.Salary.text.toString()
            }
            totalAmount = if (binding.TotalAmount.text.isNullOrEmpty()) {
                editOrder?.totalAmount.toString()
            } else {
                binding.TotalAmount.text.toString()
            }

            // 새로 수정된 주문 객체를 생성합니다.
            val supplier = Supplier(
                supplierId = editOrder?.supplier?.supplierId,
                supplierName = supplier_Name,
                supplierPhoneNumber = supplier_PhoneNumber,
                supplierLocation = supplier_Location,
            )

            val order = Order(
                orderId = editOrder?.orderId,
                product = editOrder?.product!!,
                supplier = supplier,
                company = editOrder?.company!!,
                user = editOrder?.user!!,
                date = Date(date.toLong()),
                salary = salary.toDouble(),
                totalAmount = totalAmount.toInt()
            )

            // 주문을 업데이트하는 네트워크 요청을 보냅니다.
            updateOrder(order)

            // 업데이트된 주문 정보를 전달하고 MyOrderlistActivity로 이동합니다.
            val intent = Intent(this, MyOrderlistActivity::class.java)
            intent.putExtra("intentOrder", order)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    /**
     * 주문을 업데이트하는 네트워크 요청을 보내는 함수입니다.
     * @param order Order - 수정할 주문 정보
     */
    private fun updateOrder(order: Order) {
        val call = RetrofitService.orderService.updateOrder(editOrder?.orderId!!, order)
        call.enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val updatedOrder = response.body()
                    if (updatedOrder != null) {
                        // 업데이트된 주문 데이터를 로그로 출력합니다.
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
}
