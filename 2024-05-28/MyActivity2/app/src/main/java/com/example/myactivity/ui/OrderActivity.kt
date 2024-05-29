package com.example.myactivity.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.client.OrderCli
import com.example.myactivity.data.model.Order
import com.example.myactivity.databinding.ActivityOrderBinding
import java.util.Date

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    private val orderCli: OrderCli by lazy { OrderCli(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnGetAllOrders.setOnClickListener {
                orderCli.getAllOrders()
            }

            btnGetOrderById.setOnClickListener {
                val orderId = OrderId.text.toString().toIntOrNull()
                if (orderId != null) {
                    orderCli.getOrderById(orderId)
                } else {
                    showToast("주문 ID를 올바르게 입력하세요.")
                }
            }

            btnCreateOrder.setOnClickListener {
                val order = createOrderFromInput()
                if (order != null) {
                    orderCli.createOrder(order)
                } else {
                    showToast("주문 정보를 모두 입력하세요.")
                }
            }

            btnUpdateOrder.setOnClickListener {
                val orderId = OrderId.text.toString().toIntOrNull()
                if (orderId != null) {
                    val order = createOrderFromInput()
                    if (order != null) {
                        orderCli.updateOrder(orderId, order)
                    } else {
                        showToast("주문 정보를 모두 입력하세요.")
                    }
                } else {
                    showToast("주문 ID를 올바르게 입력하세요.")
                }
            }

            btnDeleteOrder.setOnClickListener {
                val orderId = OrderId.text.toString().toIntOrNull()
                if (orderId != null) {
                    orderCli.deleteOrder(orderId)
                } else {
                    showToast("주문 ID를 올바르게 입력하세요.")
                }
            }
        }
    }

    private fun createOrderFromInput(): Order? {
        with(binding) {
            val orderId = OrderId.text.toString().toIntOrNull()
            val userId = UserId.text.toString()
            val date = editTextOrderDate.text.toString().toDate()
            val supplierName = editTextSupplierName.text.toString()
            val supplierPhoneNumber = editTextSupplierPhone.text.toString()
            val supplierLocation = "${editTextStreet.text}, ${editTextCity.text}, ${editTextState.text}, ${editTextZipcode.text}"
            val salary = editTextSalary.text.toString().toDoubleOrNull()

            return if (orderId != null && date != null && salary != null) {
                Order(orderId, userId, date, supplierName, supplierPhoneNumber, supplierLocation, salary)
            } else {
                null
            }
        }
    }

    private fun String.toDate(): Date? {
        // Date parsing logic here, implement based on your date format
        return Date()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

