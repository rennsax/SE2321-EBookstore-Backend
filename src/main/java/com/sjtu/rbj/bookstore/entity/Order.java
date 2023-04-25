package com.sjtu.rbj.bookstore.entity;

import java.sql.Timestamp;

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
import javax.persistence.Table;

import org.hibernate.annotations.Check;
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
    @Check(constraints = "`status` IN ('PENDING', 'COMPLETE', 'TRANSPORTING')")
    private OrderStatus status = OrderStatus.COMPLETE;

}
