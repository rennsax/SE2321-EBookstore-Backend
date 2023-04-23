package com.sjtu.rbj.bookstore.dao.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sjtu.rbj.bookstore.dao.BookDao;
import com.sjtu.rbj.bookstore.entity.Book;
import com.sjtu.rbj.bookstore.repository.BookRepository;

/**
 * @author Bojun Ren
 * @date 2023/04/19
 */
@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findTopX(Integer x) {
        return bookRepository.findTopX(x);
    }

    @Override
    public List<Book> findByUuid(UUID uuid) {
        return bookRepository.findByUuid(uuid);
    }
}
