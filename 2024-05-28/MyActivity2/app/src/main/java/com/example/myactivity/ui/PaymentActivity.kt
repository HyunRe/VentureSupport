package com.example.myactivity.ui
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.client.PaymentApiService
import com.example.myactivity.data.model.Payment
import com.example.myactivity.data.model.User
import com.example.myactivity.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private lateinit var paymentApiService: PaymentApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        paymentApiService = PaymentApiService(this)

        binding.btnGetPayment.setOnClickListener {
            val paymentId = binding.etPaymentId.text.toString().toIntOrNull()
            if (paymentId != null) {
                paymentApiService.getPaymentById(paymentId)
            } else {
            }
        }

        binding.btnCreatePayment.setOnClickListener {
            val payment = getPaymentFromInput()
            paymentApiService.createPayment(payment)
        }

        binding.btnUpdatePayment.setOnClickListener {
            val paymentId = binding.etPaymentId.text.toString().toIntOrNull()
            if (paymentId != null) {
                val payment = getPaymentFromInput()
                paymentApiService.updatePayment(paymentId, payment)
            } else {
            }
        }

        binding.btnDeletePayment.setOnClickListener {
            val paymentId = binding.etPaymentId.text.toString().toIntOrNull()
            if (paymentId != null) {
                paymentApiService.deletePayment(paymentId)
            } else {
            }
        }
    }

    // Helper function to get payment information from UI input
    private fun getPaymentFromInput(): Payment {
        val paymentId = binding.etPaymentId.text.toString().toIntOrNull() ?: 0
        //val userId = User() // Replace with actual user object
        val paymentName = binding.etPaymentName.text.toString()
        return Payment(paymentId, paymentName)
    }
}
