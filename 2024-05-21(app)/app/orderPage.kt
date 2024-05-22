//원장을 작성하고, 서버에 전송시키는 화면

package com.example.orderscreen
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class orderPage : AppCompatActivity() {

    private lateinit var customerNameEditText: EditText
    private lateinit var addressUserIdEditText: EditText
    private lateinit var addressNameForAddressEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var productIdEditText: EditText
    private lateinit var productNameEditText: EditText
    private lateinit var productQuantityEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customerNameEditText = findViewById(R.id.customerNameEditText)
        addressUserIdEditText = findViewById(R.id.addressUserIdEditText)
        addressNameForAddressEditText = findViewById(R.id.addressNameForAddressEditText)
        addressEditText = findViewById(R.id.addressEditText)
        productIdEditText = findViewById(R.id.productIdEditText)
        productNameEditText = findViewById(R.id.productNameEditText)
        productQuantityEditText = findViewById(R.id.productQuantityEditText)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val customerName = customerNameEditText.text.toString()
            val address = JSONObject().apply {
                put("userId", addressUserIdEditText.text.toString())
                put("nameForAddress", addressNameForAddressEditText.text.toString())
                put("address", addressEditText.text.toString())
            }
            val products = JSONObject().apply {
                put("productId", productIdEditText.text.toString())
                put("name", productNameEditText.text.toString())
                put("quantity", productQuantityEditText.text.toString())
            }

            val order = JSONObject().apply {
                put("customerName", customerName)
                put("address", address)
                put("products", products)
            }

            sendOrderToServer(order.toString())
        }
    }

    private fun sendOrderToServer(orderData: String) {
        val serverURL = "http://223.194.157.56:8080/order"
        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = orderData.toRequestBody(mediaType)

        val request = Request.Builder()
            .url(serverURL)
            .post(body)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = client.newCall(request).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@orderPage, "Data sent successfully.", Toast.LENGTH_SHORT).show()
                    } else {
                        throw Exception("Failed to send data.")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@orderPage, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
