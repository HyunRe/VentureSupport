package com.example.app.controller;

import com.example.app.model.Order;
import com.example.app.model.OrderItem;
import com.example.app.model.Payment;
import com.example.app.model.Product;
import com.example.app.model.Shipper;
import com.example.app.service.OrderService;
import com.example.app.service.PaymentService;
import com.example.app.service.ProductService;
import com.example.app.service.ShipperService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShipperService shipperService;

    // Create a new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order newOrder = orderService.save(order);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.findAll();
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get an order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") Long orderId) {
        Optional<Order> orderData = orderService.findById(orderId);

        return orderData.map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Add a product to an order
    @PostMapping("/{orderId}/products")
    public ResponseEntity<Order> addProductToOrder(@PathVariable("orderId") Long orderId, @RequestBody Product product) {
        try {
            Optional<Order> orderData = orderService.findById(orderId);

            if (orderData.isPresent()) {
                Order order = orderData.get();
                productService.save(product);
                OrderItem orderItem = new OrderItem(order, product, product.getQuantity());
                order.addItem(orderItem);
                orderService.save(order);
                return new ResponseEntity<>(order, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add payment to an order
    @PostMapping("/{orderId}/payments")
    public ResponseEntity<Order> addPaymentToOrder(@PathVariable("orderId") Long orderId,
                                                   @RequestBody PaymentRequest paymentRequest) {
        try {
            Optional<Order> orderData = orderService.findById(orderId);

            if (orderData.isPresent()) {
                Order order = orderData.get();
                Payment payment = paymentService.createPayment(order, paymentRequest.getAmount(), paymentRequest.getPaymentMethod());
                order.addPayment(payment); // Add payment to order
                orderService.save(order);

                // Assign shipper after payment is made
                Shipper shipper = shipperService.findAvailableShipper(); // Implement logic to find an available shipper
                if (shipper != null) {
                    order.setShipper(shipper);
                    orderService.save(order);
                }

                return new ResponseEntity<>(order, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete an order by ID
    @DeleteMapping("/{orderId}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderService.deleteById(orderId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PaymentRequest class to encapsulate payment data
    @Getter
    public static class PaymentRequest {
        private double amount;
        private String paymentMethod;

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }
    }
}
