package com.example.app.repository;

import com.example.app.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByUserUserId(Integer userId);
}
