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

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) // 상품 ID (외래키면서 기본키)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false) // 거래처 ID (외래키면서 기본키)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false) // 업체이용자 ID (외래키면서 기본키)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 회원 ID (외래키면서 기본키)
    private User user;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false) // 일자
    private Date date;

    @Column(name = "salary", nullable = false) // 급료
    private Double salary;

    @Column(name = "total_amount") // 총 금액
    private Integer totalAmount;

    // Getter 및 Setter 메서드
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }
}
