package com.example.app.controller;

import com.example.app.model.Income;
import com.example.app.model.Order;
import com.example.app.repository.IncomeRepository;
import com.example.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/income")
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    @GetMapping("/sales")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
