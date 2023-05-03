package com.sjtu.rbj.bookstore.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjtu.rbj.bookstore.constant.Constants;
import com.sjtu.rbj.bookstore.constant.OrderState;
import com.sjtu.rbj.bookstore.dao.BookDao;
import com.sjtu.rbj.bookstore.dao.OrderDao;
import com.sjtu.rbj.bookstore.data.BookOrdered;
import com.sjtu.rbj.bookstore.data.OrderInfo;
import com.sjtu.rbj.bookstore.entity.Book;
import com.sjtu.rbj.bookstore.entity.Order;
import com.sjtu.rbj.bookstore.entity.Order.OrderItem;
import com.sjtu.rbj.bookstore.service.OrderService;

/**
 * @author Bojun Ren
 * @data 2023/04/28
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private BookDao bookDao;

    @Override
    public OrderInfo getOrderInfoByOrderId(Integer orderId) {
        Optional<Order> maybeOrder = orderDao.findById(orderId);
        Order order = maybeOrder.orElseThrow(() -> new NoSuchElementException("no such order!"));

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(orderId);
        orderInfo.setTime(order.getTime());
        orderInfo.setState(order.getState());

        ArrayList<BookOrdered> bookOrderedList = new ArrayList<BookOrdered>();

        /** This list is a clone, not managed. */
        final List<OrderItem> orderItemList = order.getOrderItemList();
        for (OrderItem orderItem : orderItemList) {
            Book book = orderItem.getBook();
            BookOrdered bookOrdered = new BookOrdered(book.getUuid(), orderItem.getQuantity());
            bookOrderedList.add(bookOrdered);
        }
        orderInfo.setBookOrderedList(bookOrderedList);
        return orderInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitOrder(Integer orderId) {
        Optional<Order> maybeOrder = orderDao.findById(orderId);
        Order order = maybeOrder.orElseThrow(() -> new NoSuchElementException());
        if (order.getState() != OrderState.PENDING) {
            throw new UnsupportedOperationException();
        }
        order.setState(OrderState.TRANSPORTING);
        order.setTime(Timestamp.from(Instant.now()));
    }

    @Override
    public List<OrderInfo> getOrderByUserId(Integer userId) {
        List<Order> orderList = orderDao.findByUserId(userId);
        orderList.sort((o1, o2) -> {
            return o1.getTime().compareTo(o2.getTime());
        });
        List<OrderInfo> res = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getState() == OrderState.PENDING) {
                continue;
            }
            List<OrderItem> orderItemList = order.getOrderItemList();
            List<BookOrdered> bookOrderedList = new ArrayList<>();
            for (OrderItem orderItem : orderItemList) {
                bookOrderedList.add(new BookOrdered(orderItem.getBook().getUuid(), orderItem.getQuantity()));
            }
            res.add(new OrderInfo(order.getId() + Constants.ORDER_NUMBER_BIAS, order.getState(), order.getTime(),
                    bookOrderedList));
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrder(Integer orderId, UUID uuid, Integer quantity) {
        if (orderId == null || uuid == null || quantity == null) {
            throw new IllegalArgumentException("null parameters are not permitted!");
        }
        Optional<Order> maybeOrder = orderDao.findById(orderId);
        Order order = maybeOrder.orElseThrow(() -> new NoSuchElementException("no such order!"));
        if (order.getState() != OrderState.PENDING) {
            throw new UnsupportedOperationException("Only \"pending\" orders can be updated!");
        }

        Optional<Book> maybeBook = bookDao.findByUuid(uuid);
        Book targetBook = maybeBook.orElseThrow(() -> new NoSuchElementException("No such book!"));

        /** Whether the order already contains the target book. */
        final List<OrderItem> orderItemList = order.getOrderItemList();
        OrderItem targetOrderItem = null;
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getBook() == targetBook) {
                targetOrderItem = orderItem;
                break;
            }
        }

        if (targetOrderItem == null) {
            if (quantity <= 0) {
                return false;
            }
            targetOrderItem = new OrderItem();
            targetOrderItem.setBook(targetBook);
            targetOrderItem.setQuantity(quantity);
            order.addOrderItem(targetOrderItem);
            return true;
        }
        int afterQuantity = targetOrderItem.getQuantity() + quantity;
        if (afterQuantity < 0) {
            return false;
        }
        if (afterQuantity == 0) {
            order.removeOrderItem(targetOrderItem);
            return true;
        }
        targetOrderItem.setQuantity(afterQuantity);
        return true;
    }

}
