package com.sjtu.rbj.bookstore.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.sjtu.rbj.bookstore.constant.OrderStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author Bojun Ren
 * @date 2023/04/20
 */
@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "`user_id`", nullable = false)
    @Check(constraints = "`status` IN ('PENDING', 'COMPLETE', 'TRANSPORTING')")
    private Integer userId;

    @Generated(GenerationTime.INSERT)
    @Column(nullable = false)
    private Timestamp time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @PrePersist
    public void prePersistInitialize() {
        if (this.status == null) {
            this.status = OrderStatus.COMPLETE;
        }
    }

}
