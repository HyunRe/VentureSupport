package com.example.app.service;

import com.example.app.model.Income;
import com.example.app.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Income createIncome(BigDecimal amount, Long userId) {
        Income income = new Income();
        income.setAmount(amount);
        return incomeRepository.save(income);
    }
}
