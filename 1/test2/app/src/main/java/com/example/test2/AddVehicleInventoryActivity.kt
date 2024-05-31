package com.example.test2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class AddVehicleInventoryActivity : AppCompatActivity() {

    private lateinit var productNameEditText: EditText
    private lateinit var productDescriptionEditText: EditText
    private lateinit var productQuantityEditText: EditText
    private lateinit var productPriceEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addvehicleinventory)

        productNameEditText = findViewById(R.id.product_name_edit)
        productDescriptionEditText = findViewById(R.id.product_description_edit)
        productQuantityEditText = findViewById(R.id.product_quantity_edit)
        productPriceEditText = findViewById(R.id.product_description_edit)
        saveButton = findViewById(R.id.check_button)

        saveButton.setOnClickListener {
            saveProduct()
        }
    }

    private fun saveProduct() {
        val name = productNameEditText.text.toString()
        val price = productDescriptionEditText.text.toString().toDoubleOrNull() // 이제 productPrice를 사용하도록 수정
        val quantity = productQuantityEditText.text.toString().toIntOrNull()

        if (name.isNotEmpty() && price != null && quantity != null) {
            val newProduct = Product(
                productId = null,
                productName = name,
                productPrice = price,
                productQuantity = quantity
            )

            val productJson = Gson().toJson(newProduct)

            //showToast("Product saved successfully!")
        } else {
            //showToast("Please fill out all fields correctly.")
        }
    }


    /*
        private fun saveProduct() {
            val name = productNameEditText.text.toString()
            val description = productDescriptionEditText.text.toString()
            val quantity = productQuantityEditText.text.toString().toIntOrNull()
            //val price = productPriceEditText.text.toString().toDoubleOrNull()

            // Validate the input data
            //if (name.isNotEmpty() && description.isNotEmpty() && quantity != null && price != null) {
            if (name.isNotEmpty() && description.isNotEmpty() && quantity != null && price != null) {
                val newProduct = Product(
                    productId = null, // Assuming productId will be generated by the server or database
                    name = name,
                    description = description,
                    price = price,
                    quantity = quantity
                )

                // Convert product to JSON using Gson
                val productJson = Gson().toJson(newProduct)

                // Here you can send productJson to your server or database
                // For example:
                // saveProductToDatabase(productJson)

                // For demonstration, show a success message
                showToast("Product saved successfully!")
            } else {
                // Handle invalid input data
                showToast("Please fill out all fields correctly.")
            }
        }

        private fun showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Define your method to save the product in your database or server
        // private fun saveProductToDatabase(productJson: String) {
        //     // Your save logic here
        // }*/
}