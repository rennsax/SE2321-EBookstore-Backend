package com.sjtu.rbj.bookstore.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.rbj.bookstore.dao.BookDao;
import com.sjtu.rbj.bookstore.entity.Book;
import com.sjtu.rbj.bookstore.service.BookService;

/**
 * @author Bojun Ren
 * @date 2023/04/19
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    public List<Book> listBooks(Integer x) {
        x = Math.max(x, 1);
        x = Math.min(20, x);
        return bookDao.findTopX(x);
    }

    public Optional<Book> getBookByUuid(UUID uuid) {
        List<Book> res = bookDao.findByUuid(uuid);
        if (res.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(res.get(0));
    }
}
