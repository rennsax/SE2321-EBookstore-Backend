package com.sjtu.rbj.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sjtu.rbj.bookstore.entity.OrderItem;

/**
 * @author Bojun Ren
 * @data 2023/04/23
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrderId(Integer orderId);
}
