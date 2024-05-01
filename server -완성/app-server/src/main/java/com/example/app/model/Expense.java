package com.example.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="expense")
public class Expense {
    @Id
    @Column(name="expense_id")
    private int expenseId;

    @Column(name="user_id")
    private String userId;

    @Column(name="expense_record")
    private String expenseRecord;
}
