package com.example.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "income")
@IdClass(IncomeId.class)
public class Income {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @Column(name = "shipment_id")
    private Long shipmentId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "date")
    private LocalDate date;

    public Income() {
    }

    public Income(User user, Long shipmentId, double amount, LocalDate date) {
        this.user = user;
        this.shipmentId = shipmentId;
        this.amount = amount;
        this.date = date;
    }

    // Getters and Setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
