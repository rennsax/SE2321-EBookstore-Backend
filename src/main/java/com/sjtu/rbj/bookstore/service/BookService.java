package com.sjtu.rbj.bookstore.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sjtu.rbj.bookstore.entity.Book;

/**
 * @author Bojun Ren
 * @date 2023/04/19
 */
public interface BookService {
    /**
     * get {@code x} books
     * @param x the number of books to list.
     *          Constraints: {@code 1 <= x <= 20}. Otherwise, it's forced to be resized.
     * @return {@code List<Book>} with length {@code x}
     */
    List<Book> listBooks(Integer x);

    Optional<Book> getBookByUuid(UUID uuid);
}
