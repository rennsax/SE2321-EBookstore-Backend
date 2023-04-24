package com.sjtu.rbj.bookstore.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sjtu.rbj.bookstore.entity.Book;

/**
 * @author Bojun Ren
 * @date 2023/04/19
 */
public interface BookDao {

    /**
     * from mysql database, get book data with limit and offset
     * @param limit
     * @param offset
     * @return {@code List<Book>}
     */
    List<Book> findWithLimitWithOffset(Integer limit, Integer offset);

    /**
     * get book information by uuid
     * @param uuid the uuid of queried book
     * @return
     */
    Optional<Book> findByUuid(UUID uuid);
}
