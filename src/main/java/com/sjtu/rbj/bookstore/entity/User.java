package com.sjtu.rbj.bookstore.entity;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.sjtu.rbj.bookstore.constant.UserType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


/**
 * @author Bojun Ren
 * @date 2023/04/08
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @NonNull
    @Column(unique = true, nullable = false)
    private String account;

    @NonNull
    @Column(nullable = false)
    private String passwd;

    private String name;

    @Lob
    private byte[] avatar;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<Order> orderList = new ArrayList<>();

    @PrePersist
    void prePersistInitialize() {
        if (this.userType == null) {
            this.userType = UserType.NORMAL;
        }
    }
}
