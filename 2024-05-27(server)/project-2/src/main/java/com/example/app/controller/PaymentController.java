package com.example.app.controller;

import com.example.app.model.Order;
import com.example.app.model.Payment;
import com.example.app.model.User;
import com.example.app.repository.OrderRepository;
import com.example.app.repository.PaymentRepository;
import com.example.app.repository.UserRepository;
import com.example.app.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    // Process a new payment for an order
    @PostMapping("/process")
    public ResponseEntity<Object> processPayment(@RequestBody PaymentRequest request) {
        try {
            // Retrieve order
            Order order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            // Retrieve user
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Process payment
            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setUser(user);
            payment.setAmount(request.getAmount());

            payment = paymentService.processPayment(payment);

            return ResponseEntity.status(HttpStatus.CREATED).body(payment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get payments by orderId
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Payment>> getPaymentsByOrderId(@PathVariable Long orderId) {
        List<Payment> payments = paymentRepository.findByOrderId(orderId);
        if (payments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/total-income")
    public ResponseEntity<BigDecimal> getTotalIncome() {
        BigDecimal totalIncome = paymentRepository.getTotalAmountPaid();
        return ResponseEntity.ok(totalIncome);
    }

    // Internal class to represent payment request
    private static class PaymentRequest {
        private Long orderId;
        private Long userId; // Required if you have user entity
        private BigDecimal amount;

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }


}
