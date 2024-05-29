package com.example.app.model;

import java.util.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키
    @Column(name = "order_id") // 오더 ID
    private Integer orderId;

    @Setter
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false) // 일자
    private Date date;

    @Setter
    @Column(name = "supplier_name") // 거래처 이름
    private String supplierName;

    @Setter
    @Column(name = "supplier_phone_number") // 거래처 전화번호
    private String supplierPhoneNumber;

    @Setter
    @Column(name = "supplier_location") // 거래처 위치
    private String supplierLocation;

    @Setter
    @Column(name = "salary", nullable = false) // 급료
    private Double salary;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 회원 ID (외래키면서 기본키)
    private User user;

    // Getter 및 Setter 추가
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierPhoneNumber() {
        return supplierPhoneNumber;
    }

    public void setSupplierPhoneNumber(String supplierPhoneNumber) {
        this.supplierPhoneNumber = supplierPhoneNumber;
    }

    public String getSupplierLocation() {
        return supplierLocation;
    }

    public void setSupplierLocation(String supplierLocation) {
        this.supplierLocation = supplierLocation;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
