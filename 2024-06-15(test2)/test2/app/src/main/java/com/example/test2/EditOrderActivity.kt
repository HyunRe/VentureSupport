package com.example.test2

import Product
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.EditorderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditOrderActivity: AppCompatActivity() {
    private val binding: EditorderBinding by lazy {
        EditorderBinding.inflate(layoutInflater)
    }
    private var editOrder: Order? = null
    private var intentCompany: Company? = null
    private var productLists = ArrayList<Product>()
    private var order: Order? = null
    private lateinit var date: String
    private lateinit var supplier_Name: String
    private lateinit var supplier_PhoneNumber: String
    private lateinit var supplier_Location: String
    private lateinit var salary: String
    private lateinit var totalAmount: String

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        editOrder = intent.getParcelableExtra("editOrder")
        intentCompany = intent.getParcelableExtra("intentCompany")
        intent?.let { intent ->
            productLists = intent.getParcelableArrayListExtra<Product>("productLists") ?: ArrayList()
        }

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

            order = Order(
                orderId = editOrder?.orderId,
                supplier = supplier,
                company = editOrder?.company!!,
                user = null,
                date = date,
                salary = salary.toInt(),
                totalAmount = totalAmount.toInt()
            )

            val intent = Intent(this, MyOrderlistActivity::class.java)
            intent.putExtra("updatedOrder", order)
            intent.putExtra("updatedSupplier", supplier)
            intent.putExtra("intentCompany", intentCompany)
            intent.putExtra("productLists", productLists)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}