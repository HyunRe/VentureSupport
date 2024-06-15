package com.example.app.model;

import jakarta.persistence.*;

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
    @JoinColumn(name = "user_id", nullable = false) // user_id로 조인
    private User user; // User 엔티티 추가

    @Column(name = "expense_details") // 지출 내역
    private String expenseDetails;

    @Column(name = "expense_amount") // 지출액
    private Integer expenseAmount;

    @Column(name = "expense_date") // 지출일
    private String expenseDate;

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

    public Integer getExpenseAmount() { return expenseAmount; }

    public void setExpenseAmount(Integer expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }
}
