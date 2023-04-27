package com.sjtu.rbj.bookstore.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

import com.sjtu.rbj.bookstore.constant.UserType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Bojun Ren
 * @date 2023/04/08
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "`user`")
@Check(constraints = "`user_type` in ('NORMAL', 'SUPER')")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType = UserType.NORMAL;

    private String name;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "account_id", unique = true, nullable = false)
    @Embedded
    private UserAccount userAccount;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @RequiredArgsConstructor
    @ToString
    @Table(name = "`user_account`")
    @Embeddable
    public static class UserAccount {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @NonNull
        @Column(nullable = false, unique = true)
        private String account;

        @NonNull
        @Column(nullable = false)
        private String passwd;

        @ToString.Exclude
        @OneToOne(mappedBy = "userAccount")
        @JoinColumn(name = "user_id") // useless
        private User user;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("`time` desc")
    private List<Order> orderList = new ArrayList<>();

    public boolean addOrder(Order order) {
        boolean isAdded = orderList.add(order);
        order.setUser(this);
        return isAdded;
    }

    public boolean removeOrder(Order order) {
        boolean isRemoved = this.orderList.remove(order);
        if (isRemoved) {
            order.setUser(null);
        }
        return isRemoved;
    }

    public Order removeOrder(int index) {
        return this.orderList.remove(index);
    }

    public void clearOrder() {
        this.orderList.clear();
    }

}