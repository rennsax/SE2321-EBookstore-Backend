package com.sjtu.rbj.bookstore.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.sjtu.rbj.bookstore.constant.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Bojun Ren
 * @date 2023/04/20
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "`order`")
@Check(constraints = "`status` IN ('PENDING', 'COMPLETE', 'TRANSPORTING')")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(
        name = "`user_id`",
        nullable = false,
        referencedColumnName = "`id`",
        foreignKey = @ForeignKey(name = "order_user_fk")
    )
    private User user;

    @Generated(GenerationTime.INSERT)
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT(NOW())")
    private Timestamp time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.COMPLETE;

    @OneToMany(
        mappedBy = "order",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<OrderItem> orderItemSet = new HashSet<>();

    public void addOrderItem(OrderItem orderItem) {
        this.orderItemSet.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        if (this.orderItemSet.contains(orderItem)) {
            this.orderItemSet.remove(orderItem);
        }
        orderItem.setOrder(null);
    }


    @Getter
    @Setter
    @Entity
    @NoArgsConstructor
    @Table(name = "`order_item`")
    public static class OrderItem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne
        @JoinColumn(
            name = "`order_id`",
            nullable = false,
            referencedColumnName = "`id`",
            foreignKey = @ForeignKey(name = "order_item_fk")
        )
        private Order order;

        /** unidirectional association */
        @OneToOne
        @JoinColumn(
            name = "item_id",
            referencedColumnName = "`uuid`",
            foreignKey = @ForeignKey(name = "item_uuid_fk")
        )
        private Book book;

        @Column(nullable = false)
        @ColumnDefault("1")
        private Integer quantity;

        @PrePersist
        void prePersistInitialize() {
            if (quantity == null) {
                if (quantity <= 0) {
                    throw new IllegalArgumentException("Can set quantity to " + quantity.toString());
                }
                quantity = 1;
            }
        }
    }
}
