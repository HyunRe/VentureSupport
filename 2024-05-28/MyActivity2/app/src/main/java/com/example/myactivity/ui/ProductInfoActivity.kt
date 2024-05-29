package com.example.myactivity.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.client.ProductInformationClient
import com.example.myactivity.data.model.Order
import com.example.myactivity.data.model.Product
import com.example.myactivity.data.model.ProductInformation
import com.example.myactivity.data.model.ProductInformationId
import com.example.myactivity.data.model.User
import com.example.myactivity.databinding.ActivityProductInfoBinding

class ProductInformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductInfoBinding
    private lateinit var productInformationClient: ProductInformationClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productInformationClient = ProductInformationClient(this)

        binding.btnGetProductInformation.setOnClickListener {
            val orderId = binding.etOrderId.text.toString().toIntOrNull()
            val productId = binding.etProductId.text.toString().toIntOrNull()
            val userId = binding.etUserId.text.toString().toIntOrNull()

            if (orderId != null && productId != null && userId != null) {
                productInformationClient.getProductInformationById(orderId, productId, userId)
            } else {
                Toast.makeText(this, "Please enter valid order ID, product ID, and user ID", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCreateProductInformation.setOnClickListener {
            val productInformation = getProductInformation()
            productInformationClient.createProductInformation(productInformation)
        }

        binding.btnUpdateProductInformation.setOnClickListener {
            val productInformation = getProductInformation()
            productInformationClient.updateProductInformation(productInformation)
        }

        binding.btnDeleteProductInformation.setOnClickListener {
            val orderId = binding.etOrderId.text.toString().toIntOrNull()
            val productId = binding.etProductId.text.toString().toIntOrNull()
            val userId = binding.etUserId.text.toString().toIntOrNull()

            if (orderId != null && productId != null && userId != null) {
                productInformationClient.deleteProductInformation(orderId, productId, userId)
            } else {
                Toast.makeText(this, "Please enter valid order ID, product ID, and user ID", Toast.LENGTH_SHORT).show()
            }
        }
        private fun getProductInformation(): ProductInformation {
            val orderId = binding.etOrderId.text.toString().toIntOrNull() ?: 0
            val productId = binding.etProductId.text.toString().toIntOrNull() ?: 0
            val userId = binding.etUserId.text.toString().toIntOrNull() ?: 0
            return ProductInformation(
                ProductInformationId(orderId, productId, userId),
                Order(), // Replace with actual order object
                Product(), // Replace with actual product object
                User() // Replace with actual user object
            )
        }
    }
}
