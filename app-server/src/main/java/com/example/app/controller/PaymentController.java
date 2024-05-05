package com.example.app.controller;

import com.example.app.model.Payment;
import com.example.app.model.PaymentRequest;
import com.example.app.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    // API để thực hiện thanh toán cho một đơn hàng
    @PostMapping("/process-payment")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            // Xử lý thanh toán ở đây, ví dụ: lưu thông tin thanh toán vào cơ sở dữ liệu
            Payment payment = new Payment(paymentRequest.getUserId());
            paymentRepository.save(payment);

            return ResponseEntity.ok("Payment processed successfully!");
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing payment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
