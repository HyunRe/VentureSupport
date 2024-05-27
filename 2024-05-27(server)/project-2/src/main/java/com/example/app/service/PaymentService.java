package com.example.app.service;

import com.example.app.model.Payment;
import com.example.app.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(Payment payment) {
        // Validate payment, process business logic, etc.
        // For demonstration, we'll just save the payment
        return paymentRepository.save(payment);
    }
}
