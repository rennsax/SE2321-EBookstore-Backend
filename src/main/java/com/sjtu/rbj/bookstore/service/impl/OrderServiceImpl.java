package com.sjtu.rbj.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ArrayList<BookOrdered> bookOrderedList = new ArrayList<BookOrdered>();
        List<OrderItem> orderItemList = order.getOrderItemList();
        for (OrderItem orderItem : orderItemList) {
            Optional<Book> maybeBook = bookDao.findById(orderItem.getId());
            Book book = maybeBook.orElseThrow(() -> new RuntimeException("unexpected book not found error!"));
            BookOrdered bookOrdered = new BookOrdered(book.getUuid(), orderItem.getQuantity());
            bookOrderedList.add(bookOrdered);
        }
        orderInfo.setBookOrderedList(bookOrderedList);
        return orderInfo;
    }


}
