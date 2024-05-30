package com.example.test2

import Order
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.text.SimpleDateFormat

class EditOrderActivity: AppCompatActivity() {
    private lateinit var dateEditText: EditText
    private lateinit var supplierNameEditText: EditText
    private lateinit var supplierPhoneNumberEditText: EditText
    private lateinit var supplierLocationEditText: EditText
    private lateinit var salaryEditText: EditText
    private lateinit var totalAmountEditText: EditText
    private lateinit var updateButton: Button

    fun onCreate(savedInstanceState: Bundle?, intent: Any) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editorder)

        dateEditText = findViewById(R.id.user_email)
        supplierNameEditText = findViewById(R.id.Supplier_Name)
        supplierPhoneNumberEditText = findViewById(R.id.Supplier_PhoneNumber)
        supplierLocationEditText = findViewById(R.id.Supplier_Location)
        salaryEditText = findViewById(R.id.Salary)
        totalAmountEditText = findViewById(R.id.Total_Amount)
        updateButton = findViewById(R.id.update_button)

        // Assuming you are passing the order as a JSON string
        val orderJson = intent.getStringExtra("order")
        val order = Gson().fromJson(orderJson, Order::class.java).also {
        }

        updateButton.setOnClickListener {
            updateOrder(order)
        }
    }

    private fun populateOrderDetails(order: Order) {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd.E")
        dateEditText.setText(dateFormat.format(order.date))
        supplierNameEditText.setText(order.supplierName)
        supplierPhoneNumberEditText.setText(order.supplierPhoneNumber)
        supplierLocationEditText.setText(order.supplierLocation)
        salaryEditText.setText(order.salary.toString())
        totalAmountEditText.setText(order.totalAmount.toString())
    }

    private fun updateOrder(order: Order) {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd.E")
        val updatedOrder = order.copy(
            date = dateFormat.parse(dateEditText.text.toString()) ?: order.date,
            supplierName = supplierNameEditText.text.toString(),
            supplierPhoneNumber = supplierPhoneNumberEditText.text.toString(),
            supplierLocation = supplierLocationEditText.text.toString(),
            salary = salaryEditText.text.toString().toDoubleOrNull() ?: order.salary,
            totalAmount = totalAmountEditText.text.toString().toIntOrNull() ?: order.totalAmount
        )

        // Send updatedOrder to your server or database
        // For example:
        // updateOrderInDatabase(updatedOrder)
    }

    // Define your method to update the order in your database or server
    // private fun updateOrderInDatabase(order: Order) {
    //     // Your update logic here
    // }
}