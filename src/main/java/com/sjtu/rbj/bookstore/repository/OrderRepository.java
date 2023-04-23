package com.sjtu.rbj.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sjtu.rbj.bookstore.entity.Order;

/**
 * @author Bojun Ren
 * @data 2023/04/23
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
    /**
     * find all orders belonging to the user
     * @param userId
     * @return
     */
    List<Order> findByUserId(Integer userId);
}
