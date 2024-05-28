// OrderController.java
package com.example.app.controller;

import com.example.app.model.Order;
import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import com.example.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // SELECT (All)
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // SELECT
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody Order order) {
        // Assuming user_id is provided in the request body
        if (order.getUser() == null || order.getUser().getUserId() == null) {
            return ResponseEntity.badRequest().body("User ID must be provided");
        }

        // Validate user exists
        Optional<User> userOptional = userRepository.findById(order.getUser().getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        order.setUser(user);

        // Save order
        order = orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
