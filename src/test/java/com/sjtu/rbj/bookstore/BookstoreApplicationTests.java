package com.sjtu.rbj.bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.sjtu.rbj.bookstore.constant.OrderStatus;
import com.sjtu.rbj.bookstore.dao.OrderDao;
import com.sjtu.rbj.bookstore.dao.UserDao;
import com.sjtu.rbj.bookstore.entity.Book;
import com.sjtu.rbj.bookstore.entity.Order;
import com.sjtu.rbj.bookstore.entity.Order.OrderItem;
import com.sjtu.rbj.bookstore.entity.User;
import com.sjtu.rbj.bookstore.entity.User.UserAccount;
import com.sjtu.rbj.bookstore.repository.BookRepository;
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
    OrderRepository orderRepository;

    @Autowired
    UserDao userDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserService userService;

    @Autowired
    DataSource dataSource;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    EntityManager entityManager;

    EntityTransaction transaction;

    public void initialEm() {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    public void destroyEm() {
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testUserService() {
        assertTrue(userService.enableLogin("cauchy@gmail.com", "123456"));
    }

    /**
     * test whether the entities can be correctly performed
     */
    @Test
    @Transactional
    void testEntities() {
        /** `book` table (initial data from "data-mysql.sql") */
        long bookCount = bookRepository.count();
        assertEquals(46, bookCount, "books count error!");

        /** `user` table */
        Optional<User> maybeUser = userRepository.findByUserAccountAccountAndUserAccountPasswd("cauchy@gmail.com", "123456");
        assertTrue(maybeUser.isPresent(), "user \"cauchy\" not found!");
        User user = maybeUser.get();
        user.setName("cauchy");
        assertEquals(2, user.getOrderList().size());

        /** `order` table */
        List<Order> orderAll = orderRepository.findAll();
        assertEquals(2, orderAll.size());
        Order order = orderAll.get(1); /** the second order, PENDING, contains two items */
        assertEquals(user, order.getUser());
        assertEquals(3, order.getOrderItemList().size());
    }

    // @BeforeEach
    @Test
    @Transactional
    @Rollback(false)
    void initializeDatabase() {
        /** before this initialization, data-mysql.sql has been loaded */
        /** BEGIN insert book */
        Book book = new Book();
        book.setTitle("~test book~");
        assertNull(book.getUuid());
        bookRepository.save(book);
        assertNotNull(book.getUuid());
        /** END insert book */

        /** insert some relations */
        /** insert a user */
        User user = new User();
        user.setUserAccount(new UserAccount("cauchy", "123"));
        userRepository.save(user);

        /** insert two orders */
        Order order1 = new Order();
        order1.setUser(user);
        Order order2 = new Order();
        order2.setStatus(OrderStatus.PENDING);
        order2.setUser(user);

        orderRepository.save(order1);
        assertEquals(orderRepository.count(), 1);
        orderRepository.save(order2);

        /** find two books for order items of order2 */
        Optional<Book> book1 = bookRepository.findById(10);
        Optional<Book> book2 = bookRepository.findById(20);
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setBook(book1.get());
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setBook(book2.get());
        orderItem1.setQuantity(1);
        orderItem2.setQuantity(2);

        /** insert two order items for order2 */
        order2.addOrderItem(orderItem1);
        order2.addOrderItem(orderItem2);

        /** find one books for order items of order1 */
        book1 = bookRepository.findById(15);
        orderItem1 = new OrderItem();
        orderItem1.setQuantity(3);
        orderItem1.setBook(book1.get());

        /** insert two order items for order1 */
        order1.addOrderItem(orderItem1);
    }

    @Test
    // TODO confusing yet
    void testUserAddOrder() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        System.out.println("=========");
        // initialEm();
        // User user = entityManager.find(User.class, 1);
        // User user = userRepository.findById(1).get();
        User user = userRepository.findAll().get(0);
        System.out.println(entityManager.contains(user));
        System.out.println(user.getOrderList().size());
        System.out.println("=========");
        transaction.commit();
        // destroyEm();
    }

}