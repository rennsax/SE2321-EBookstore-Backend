package com.sjtu.rbj.bookstore;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.sjtu.rbj.bookstore.constant.OrderStatus;
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
        System.out.println(order.toString());
        orderRepository.save(order);
        System.out.println(order.toString());
    }

    @Test
    @Rollback(false)
    void initializeDatabase() {
        /** insert some relations */
        User user = new User();
        user.setUserAccount(new UserAccount("cauchy", "123"));
        userRepository.save(user);
        Order order1 = new Order();
        order1.setUser(user);
        Order order2 = new Order();
        order2.setStatus(OrderStatus.PENDING);
        order2.setUser(user);
        orderRepository.save(order1);
        orderRepository.save(order2);
    }

    @Test
    @Transactional
    @Rollback(false)
    void testUserOwnOrders() {
        User user = userRepository.findById(1).get();
        System.out.println(user);
        user.removeOrder(user.getOrderList().get(0));
        System.out.println(user);
    }

}

@DataJpaTest
class DataTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;

    void initializeDatabase() {
        /** insert some relations */
        User user = new User();
        user.setUserAccount(new UserAccount("cauchy", "123"));
        userRepository.save(user);
        Order order1 = new Order();
        order1.setUser(user);
        Order order2 = new Order();
        order2.setStatus(OrderStatus.PENDING);
        order2.setUser(user);
        orderRepository.save(order1);
        orderRepository.save(order2);
    }

    @Test
    void testUserOwnOrders() {
        initializeDatabase();

        User user = userRepository.findById(1).get();
        System.out.println(user);
        user.removeOrder(user.getOrderList().get(0));
        System.out.println(user);
    }
}