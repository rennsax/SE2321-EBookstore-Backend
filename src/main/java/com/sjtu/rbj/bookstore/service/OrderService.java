package com.sjtu.rbj.bookstore.service;

import com.sjtu.rbj.bookstore.data.OrderInfo;

public interface OrderService {
    OrderInfo getOrderInfoByOrderId(Integer orderId);
}
