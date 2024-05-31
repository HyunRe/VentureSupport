// OrderController.java
package com.example.app.controller;

import com.example.app.model.Order;
import com.example.app.model.User;
import com.example.app.model.Warehouse;
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


    @GetMapping("/users/{id}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Integer id) {
        List<Order> order = orderService.getOrdersByUserId(id);
        if (!order.isEmpty()) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/suppliers/{id}")
    public ResponseEntity<List<Order>> getOrdersBySupplierId(@PathVariable Integer id) {
        List<Order> orders = orderService.getOrdersBySupplierId(id);
        if (!orders.isEmpty()) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<List<Order>> getOrdersByCompanyId(@PathVariable Integer id) {
        List<Order> orders = orderService.getOrdersByCompanyId(id);
        if (!orders.isEmpty()) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order orderDetails) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.setProduct(orderDetails.getProduct());
            existingOrder.setSupplier(orderDetails.getSupplier());
            existingOrder.setCompany(orderDetails.getCompany());
            existingOrder.setUser(orderDetails.getUser());
            existingOrder.setDate(orderDetails.getDate());
            existingOrder.setSalary(orderDetails.getSalary());
            existingOrder.setTotalAmount(orderDetails.getTotalAmount());
            return ResponseEntity.ok(orderService.saveOrder(existingOrder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        if (orderService.getOrderById(id).isPresent()) {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
