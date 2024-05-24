package com.example.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "loaded_quantity")
    private int loadedQuantity;

    // Constructors, getters, setters

    public OrderItem(Order order, Product product, int quantity, User user, int loadedQuantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.user = user;
        this.loadedQuantity = loadedQuantity;
    }
}
