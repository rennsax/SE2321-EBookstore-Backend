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


    @Override
    public Optional<Book> getBookByUuid(UUID uuid) {
        return bookDao.findByUuid(uuid);
    }

    @Override
    public List<Book> getBookListForHomePage(Integer limit) {
        return this.getBookListForHomePage(limit, 0);
    }

    @Override
    public List<Book> getBookListForHomePage(Integer limit, Integer offset) {
        /** resize limit */
        limit = Math.max(limit, 1);
        limit = Math.min(20, limit);
        return bookDao.findWithLimitWithOffset(limit, offset);
    }

}
