package com.example.app.controller;

import com.example.app.model.Income;
import com.example.app.model.User;
import com.example.app.repository.IncomeRepository;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all incomes
    @GetMapping
    public ResponseEntity<List<Income>> getAllIncomes() {
        List<Income> incomes = incomeRepository.findAll();
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }

    // Get income by ID
    @GetMapping("/{id}")
    public ResponseEntity<Income> getIncomeById(@PathVariable Long id) {
        Optional<Income> incomeOptional = incomeRepository.findById(id);
        if (incomeOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(incomeOptional.get(), HttpStatus.OK);
    }

    // Create a new income
    @PostMapping
    public ResponseEntity<Object> createIncome(@RequestBody IncomeRequest request) {
        // Retrieve user
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();

        // Create income
        Income income = new Income();
        income.setUser(user);
        income.setAmount(request.getAmount());

        income = incomeRepository.save(income);
        return ResponseEntity.status(HttpStatus.CREATED).body(income);
    }

    // Get total income amount
    @GetMapping("/total-amount")
    public ResponseEntity<BigDecimal> getTotalIncomeAmount() {
        BigDecimal totalAmount = incomeRepository.getTotalIncomeAmount();
        return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }

    // Get total income amount by user
    @GetMapping("/total-amount-by-user/{userId}")
    public ResponseEntity<BigDecimal> getTotalIncomeAmountByUser(@PathVariable Long userId) {
        BigDecimal totalAmount = incomeRepository.getTotalIncomeAmountByUser(userId);
        return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }

    // Internal class to represent income request
    private static class IncomeRequest {
        private Long userId;
        private BigDecimal amount;

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

    // Other endpoints as needed
}
