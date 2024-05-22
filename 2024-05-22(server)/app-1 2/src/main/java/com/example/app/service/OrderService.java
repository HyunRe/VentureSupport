package com.example.app.service;

import com.example.app.model.Order;

import com.example.app.model.Address;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order save(Order order);

    List<Order> findAll();

    Optional<Order> findById(Long id);

    void deleteById(Long id);

    Order addAddressToOrder(Long orderId, Address address);
}
