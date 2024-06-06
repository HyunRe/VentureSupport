package com.example.app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키
    @Column(name = "expense_id") // 지출 ID
    private Integer expenseId;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false) // 결제 ID (외래키)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 회원 ID (외래키면서 기본키)
    private User user;

    @Column(name = "expense_details") // 지출 내역
    private String expenseDetails;

    @Column(name = "expense_amount") // 지출액
    private Double expenseAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expense_date") // 지출일
    private Date expenseDate;

    // Getters and Setters
    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getExpenseDetails() {
        return expenseDetails;
    }

    public void setExpenseDetails(String expenseDetails) {
        this.expenseDetails = expenseDetails;
    }

    public Double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }
}
