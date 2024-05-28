package com.example.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product information")
public class ProductInformation {

    @EmbeddedId
    private ProductInformationId id;

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    @ManyToOne
    @MapsId("userId")
    private User user;

    // 생성자, Getter 및 Setter
    public ProductInformation() {}

    public ProductInformation(ProductInformationId id, Order order, Product product, User user) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.user = user;
    }

    public ProductInformationId getId() {
        return id;
    }

    public void setId(ProductInformationId id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
