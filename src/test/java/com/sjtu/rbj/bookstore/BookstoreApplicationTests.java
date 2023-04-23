package com.sjtu.rbj.bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjtu.rbj.bookstore.dao.OrderDao;
import com.sjtu.rbj.bookstore.dao.UserDao;
import com.sjtu.rbj.bookstore.data.UserInfo;
import com.sjtu.rbj.bookstore.entity.Order;
import com.sjtu.rbj.bookstore.entity.OrderItem;
import com.sjtu.rbj.bookstore.entity.User;
import com.sjtu.rbj.bookstore.repository.BookRepository;
import com.sjtu.rbj.bookstore.repository.OrderItemRepository;
import com.sjtu.rbj.bookstore.repository.OrderRepository;
import com.sjtu.rbj.bookstore.repository.UserRepository;
import com.sjtu.rbj.bookstore.service.UserService;

@SpringBootTest
class BookstoreApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserDao userDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserService userService;

    @Test
    void testUserDao() {
        Optional<User> res = userDao.findByAccount("cauchy@gmail.com");
        assertTrue(res.isPresent());
        res = userRepository.findByAccount("123");
        assertFalse(res.isPresent());
    }

    @Test
    void testBookRepository() {
    }

    @Test
    void testOrderItemRepository() {
        List<OrderItem> res = orderItemRepository.findByOrderId(1);
        System.out.println(res.toString());
    }

    @Test
    void testOrderDao() {
        List<Order> res = orderDao.findByUserId(2);
        System.out.println(res.toString());
    }

    @Test
    void testUserService() {
        UserInfo res = userService.getUserInfoByAccount("cauchy@gmail.com");
        System.out.println(res.toString());
        assertEquals(res.getId(), 2);
        assertEquals(res.getOrderId(), 1);
    }

}
