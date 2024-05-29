package com.example.myactivity.ui
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.client.ProductCli
import com.example.myactivity.data.model.Product
import com.example.myactivity.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var productCli: ProductCli

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productCli = ProductCli(this)

        binding.btnGetAProduct.setOnClickListener {
            val productId = binding.etProductId.text.toString().toIntOrNull()
            if (productId != null) {
                productCli.getProductById(productId)
            } else {
            }
        }

        binding.btnCreateAProduct.setOnClickListener {
            val product = getProductFromInput()
            productCli.createProduct(product)
        }

        binding.btnUpdateAProduct.setOnClickListener {
            val productId = binding.etProductId.text.toString().toIntOrNull()
            if (productId != null) {
                val product = getProductFromInput()
                productCli.updateProduct(productId, product)
            } else {
            }
        }

        binding.btnDeleteAProduct.setOnClickListener {
            val productId = binding.etProductId.text.toString().toIntOrNull()
            if (productId != null) {
                productCli.deleteProduct(productId)
            } else {
            }
        }
    }

    // Helper function to get product information from UI input
    private fun getProductFromInput(): Product {
        val productId = binding.etProductId.text.toString().toIntOrNull() ?: 0
        val name = binding.ProductName.text.toString()
        val description = binding.ProductDescription.text.toString()
        val price = binding.ProductPrice.text.toString().toDoubleOrNull() ?: 0.0
        val quantity = binding.ProductQuantity.text.toString().toIntOrNull() ?: 0
        return Product(productId, name, description, price, quantity)
    }
}
