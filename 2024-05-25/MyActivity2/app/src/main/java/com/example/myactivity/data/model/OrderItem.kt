package com.example.myactivity.data.model

//주문상품 정보
import androidx.constraintlayout.compose.GeneratedValue //import가 안 됨
import androidx.room.Entity


@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "order_id")
    var order: Order? = null,

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null,

    @Column(name = "quantity")
    var quantity: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @Column(name = "loaded_quantity")
    var loadedQuantity: Int = 0
)
