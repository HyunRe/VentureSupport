package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.EditorderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

class EditOrderActivity: AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        editOrder = intent.getParcelableExtra("editOrder")

        binding.dateTextView.setText(editOrder?.date.toString(), TextView.BufferType.EDITABLE)
        binding.SupplierNameTextView.setText(editOrder?.supplier?.supplierName, TextView.BufferType.EDITABLE)
        binding.SupplierPhoneNumberTextView.setText(editOrder?.supplier?.supplierPhoneNumber, TextView.BufferType.EDITABLE)
        binding.SupplierLocationTextView.setText(editOrder?.supplier?.supplierLocation, TextView.BufferType.EDITABLE)
        binding.SalaryTextView.setText(editOrder?.salary.toString(), TextView.BufferType.EDITABLE)
        binding.TotalAmountTextView.setText(editOrder?.totalAmount.toString(), TextView.BufferType.EDITABLE)

        binding.updateButton.setOnClickListener {
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

            val supplier = Supplier (
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

            updateOrder(order)
            val intent = Intent(this, MyOrderlistActivity::class.java)
            intent.putExtra("intentOrder", order)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun updateOrder(order: Order) {
        val call = RetrofitService.orderService.updateOrder(editOrder?.orderId!!, order)
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
}