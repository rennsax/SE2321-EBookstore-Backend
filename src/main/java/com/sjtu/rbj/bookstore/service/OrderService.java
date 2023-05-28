package com.sjtu.rbj.bookstore.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.sjtu.rbj.bookstore.dto.OrderInfoDTO;
import com.sjtu.rbj.bookstore.entity.Order;

/**
 * @author Bojun Ren
 * @data 2023/04/29
 */
public interface OrderService {
    /**
     * Get necessary order information by order id.
     *
     * @param orderId must not be {@literal null}.
     * @return OrderInfo
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     * @throws NoSuchElementException if no such order.
     */
    OrderInfoDTO getOrderInfoByOrderId(Integer orderId);

    /**
     * Submit a "pending" order.
     *
     * <p>In fact, this process change the order's state
     * from "pending" to "transporting".</p>
     *
     * @param orderId must not be {@literal null}.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     * @throws NoSuchElementException if no such order.
     * @throws UnsupportedOperationException if the target order isn't "pending".
     *
     */
    void submitOrder(Integer orderId);

    /**
     * Get all orders by user id (except the "pending" order).
     *
     * @param userId must not be {@literal null}.
     * @return all orders (except a "pending" order) belonging to the user, packed as {@code OrderInfo}
	 * @throws IllegalArgumentException if {@literal userId} is {@literal null}.
     */
    List<Order> getOrderByUserId(Integer userId);

    /**
     * Update the ordered item(s). Only "pending" orders' items can be updated.
     *
     * <p>If given {@literal quantity} is positive, add ordered item(s).
     * Otherwise, <strong>try</strong> to delete items.</p>
     *
     * @param orderId must not be {@literal null}.
     * @param uuid must not be {@literal null}.
     * @param quantity must not be {@literal null}.
     * @return true on successfully update the ordered item(s).
     *         A positive {@literal quantity} always leads to a {@code true} returned value.
	 * @throws IllegalArgumentException if any of the parameters is {@literal null}.
     * @throws NoSuchElementException if no such order or no such book.
     * @throws UnsupportedOperationException if the target order isn't "pending".
     */
    boolean updateOrder(Integer orderId, UUID uuid, Integer quantity);
}
