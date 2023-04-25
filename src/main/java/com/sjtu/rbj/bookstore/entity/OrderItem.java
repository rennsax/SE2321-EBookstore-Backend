package com.sjtu.rbj.bookstore.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bojun Ren
 * @data 2023/04/23
 */
@Data
@Entity
@Table(name = "`order_item`")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "item_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID itemId;

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
