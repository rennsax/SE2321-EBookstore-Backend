package com.sjtu.rbj.bookstore.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.rbj.bookstore.constant.OrderStatus;
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

    @Override
    public boolean enableLogin(String account, String passwd) {
        Optional<User> maybeUser = userDao.findByAccountAndPasswd(account, passwd);
        return maybeUser.isPresent();
    }

    @Override
    public UserInfo getUserInfoByAccount(String account) {
        Optional<User> maybeUser = userDao.findByAccount(account);
        User user = maybeUser.orElseThrow(() -> new NoSuchElementException("Cannot find such account!"));
        List<Order> orderList = user.getOrderList();

        Order orderPending = null;
        for (Order order : orderList) {
            if (OrderStatus.PENDING == order.getStatus()) {
                orderPending = order;
                break;
            }
        }
        if (orderPending == null) {
            orderPending = new Order();
            orderPending.setStatus(OrderStatus.PENDING);
            user.addOrder(orderPending);
            userDao.flush();
        }
        return new UserInfo(user.getId(), orderPending.getId());
    }
}
