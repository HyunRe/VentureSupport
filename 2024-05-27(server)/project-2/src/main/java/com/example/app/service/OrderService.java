package com.example.app.service;

import com.example.app.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId, List<Long> productIds);
}
