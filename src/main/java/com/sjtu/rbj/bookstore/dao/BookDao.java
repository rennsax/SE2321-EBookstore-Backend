package com.sjtu.rbj.bookstore.dao;

import java.util.List;
import java.util.UUID;

import com.sjtu.rbj.bookstore.entity.Book;

/**
 * @author Bojun Ren
 * @date 2023/04/19
 */
public interface BookDao {
    /**
     * find the default first {@code x} books
     * @param x the number of book to query for
     * @return
     */
    List<Book> findTopX(Integer x);

    List<Book> findByUuid(UUID uuid);
}
