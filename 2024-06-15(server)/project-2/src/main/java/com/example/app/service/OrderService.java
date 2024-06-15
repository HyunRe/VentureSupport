// OrderService.java
package com.example.app.service;

import com.example.app.model.Order;
import com.example.app.model.User;
import com.example.app.model.Warehouse;
import com.example.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserUserId(userId);
    }

    public List<Order> getOrdersByCompanyId(Integer companyId) {
        return orderRepository.findByCompanyCompanyId(companyId);
    }

    public List<Order> getOrdersBySupplierId(Integer supplierId) {
        return orderRepository.findBySupplierSupplierId(supplierId);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Integer id, Order order) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            order.setOrderId(id);
            return orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order not found with id: " + id);
        }
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}
