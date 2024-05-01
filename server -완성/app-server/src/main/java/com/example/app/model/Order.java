package com.example.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private int orderId;

    @Column(name="customer_name")
    private String customerName;

    @Column(name="address")
    private String address;

    @OneToMany(mappedBy = "order")
    private List<Product> products;

    // Constructors
    public Order() {
    }

    public Order(int orderId, String customerName, String address, List<Product> products) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.address = address;
        this.products = products;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
