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
     * Get book information for homepage, from the top of the database
     * @param limit the maximum number of books to get.
     *        Constraints: {@code 1 <= limit <= 20}. Otherwise, it's forced to be resized.
     * @return {@code List<Book>} with size no more than {@code limit}
     */
    List<Book> getBookListForHomePage(Integer limit);

    /**
     * Get book information for homepage, from the top of the database
     * @param limit the maximum number of books to get
     * @param offset the beginning row number to get book information
     * @return {@code List<Book>}
     */
    List<Book> getBookListForHomePage(Integer limit, Integer offset);

    /**
     * get book information by uuid
     * @param uuid the book uuid
     * @return {@code Optional<Book>}
     */
    Optional<Book> getBookByUuid(UUID uuid);
}
