package com.sjtu.rbj.bookstore;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjtu.rbj.bookstore.dao.OrderDao;
import com.sjtu.rbj.bookstore.dao.UserDao;
import com.sjtu.rbj.bookstore.data.UserInfo;
import com.sjtu.rbj.bookstore.entity.Book;
import com.sjtu.rbj.bookstore.entity.Order;
import com.sjtu.rbj.bookstore.entity.OrderItem;
import com.sjtu.rbj.bookstore.entity.User;
import com.sjtu.rbj.bookstore.entity.User.UserAccount;
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

    /**
     * test whether the entities can be correctly performed
     */
    @Test
    void testEntities() {
        Book book = new Book();
        book.setTitle("Test Book");
        bookRepository.save(book);

        User user = new User();
        User.UserAccount userAccount = new UserAccount("cauchy@gmail.com", "123456");
        user.setUserAccount(userAccount);
        userRepository.save(user);

        List<User> res = userRepository.findAll();

        System.out.println("res" + res);
        System.out.println(userAccount);
        Optional<User> res2 = userRepository.findByUserAccountAccountAndUserAccountPasswd("cauchy@gmail.com", "123456");
        System.out.println(res2);
    }

    @Test
    void testUserDao() {
    }

    @Test
    void testBookRepository() {
        /** BEGIN insert book */
        Book book = new Book();
        book.setTitle("~test book~");
        assertNull(book.getUuid());
        bookRepository.save(book);
        assertNotNull(book.getUuid());
        /** END insert book */
    }

    @Test
    void testOrderItemRepository() {
        List<OrderItem> res = orderItemRepository.findByOrderId(1);
        System.out.println(res.toString());
    }

    @Test
    void testOrderDao() {
        List<Order> res = orderDao.findByUserId(2);
        System.out.println(res.get(0).getTime());
    }

    @Test
    void testUserService() {
        UserInfo res = userService.getUserInfoByAccount("cauchy@gmail.com");
        System.out.println(res.toString());
    }

    @Test
    void testUserRepository() {
        Order order = new Order();
        order.setUserId(1);
        System.out.println(order.toString());
        orderRepository.save(order);
        System.out.println(order.toString());
    }

    @Test
    void testTableAssociation() {
        System.out.println(1);
    }

}
