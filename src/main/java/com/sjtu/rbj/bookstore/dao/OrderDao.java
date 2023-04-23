package com.sjtu.rbj.bookstore.dao;

import java.util.List;

import com.sjtu.rbj.bookstore.entity.Order;

/**
 * @author Bojun Ren
 * @data 2023/04/23
 */
public interface OrderDao {
    /**
     * save an entity to the database
     * @param <S>
     * @param entity
     * @return
     */
    <S extends Order> S save(S entity);

    /**
     * find all order of an user
     *
     * @param userId
     * @return
     */
    List<Order> findByUserId(Integer userId);
}
