package com.example.app.controller;

import com.example.app.model.Order;
import com.example.app.model.User;
import com.example.app.repository.OrderRepository;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Get order by order ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        return orderOptional.map(order -> new ResponseEntity<>(order, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new order
    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody Order order) {
        // Assuming user_id is provided in the request body
        if (order.getUser() == null || order.getUser().getId() == null) {
            return ResponseEntity.badRequest().body("User ID must be provided");
        }

        // Validate user exists
        Optional<User> userOptional = userRepository.findById(order.getUser().getId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        order.setUser(user);

        // Save order
        order = orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // Update an existing order
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order updatedOrder) {
        Optional<Order> existingOrderOptional = orderRepository.findById(orderId);
        if (existingOrderOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order existingOrder = existingOrderOptional.get();

        // Validate and update order details
        if (updatedOrder.getOrderNumber() != null) {
            existingOrder.setOrderNumber(updatedOrder.getOrderNumber());
        }
        if (updatedOrder.getOrderDate() != null) {
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
        }
        if (updatedOrder.getUser() != null && updatedOrder.getUser().getId() != null) {
            Optional<User> userOptional = userRepository.findById(updatedOrder.getUser().getId());
            if (userOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            existingOrder.setUser(userOptional.get());
        }

        // Save updated order
        existingOrder = orderRepository.save(existingOrder);
        return ResponseEntity.ok(existingOrder);
    }

    // Delete an order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        orderRepository.deleteById(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
