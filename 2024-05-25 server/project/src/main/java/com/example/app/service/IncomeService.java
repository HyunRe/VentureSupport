package com.example.app.service;

import com.example.app.model.Income;
import com.example.app.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public Income save(Income income) {
        return incomeRepository.save(income);
    }

    public List<Income> findAll() {
        return incomeRepository.findAll();
    }

    public List<Income> findByUserId(Long userId) {
        return incomeRepository.findByUserId(userId);
    }

    public List<Income> findByDate(LocalDate date) {
        return incomeRepository.findByDate(date);
    }
}
