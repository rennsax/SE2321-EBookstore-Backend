package com.sjtu.rbj.bookstore.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.rbj.bookstore.constant.OrderStatus;
import com.sjtu.rbj.bookstore.dao.OrderDao;
import com.sjtu.rbj.bookstore.dao.UserDao;
import com.sjtu.rbj.bookstore.data.UserInfo;
import com.sjtu.rbj.bookstore.entity.Order;
import com.sjtu.rbj.bookstore.entity.User;
import com.sjtu.rbj.bookstore.service.UserService;

/**
 * @author Bojun Ren
 * @date 2023/04/18
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public boolean enableLogin(String account, String passwd) {
        return userDao.findByAccountAndPasswd(account, passwd).isPresent();
    }

    @Override
    public UserInfo getUserInfoByAccount(String account) {
        Optional<User> userRes = userDao.findByAccount(account);
        User user = userRes.orElseThrow(() -> new NoSuchElementException("Cannot find such account!"));
        return this.getUserInfoById(user.getId());
    }

    private UserInfo getUserInfoById(Integer userId) {
        List<Order> orderAllList = orderDao.findByUserId(userId);
        Order orderPending = null;
        for (Order order : orderAllList) {
            if (OrderStatus.PENDING.getStatus().equals(order.getStatus())) {
                orderPending = order;
                break;
            }
        }
        /**
         * If current user have no pending order, create one
         */
        if (orderPending == null) {
            orderPending = new Order(userId);
            orderPending.setStatus(OrderStatus.PENDING.getStatus());
            orderDao.save(orderPending);
        }
        return new UserInfo(userId, orderPending.getId());
    }
}
