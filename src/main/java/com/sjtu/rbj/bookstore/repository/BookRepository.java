package com.sjtu.rbj.bookstore.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sjtu.rbj.bookstore.entity.Book;

/**
 * @author Bojun Ren
 * @data 2023/04/19
 */
public interface BookRepository extends JpaRepository<Book, UUID> {

    /**
     * find book by uuid
     * @param uuid the book uuid
     * @return {@code Optional<Book>}
     */
    Optional<Book> findByUuid(UUID uuid);

    /**
     * from mysql database, get book data with limit and offset
     * @param limit
     * @param offset
     * @return {@code List<Book>}
     */
    @Query(value = "select * from `book` limit ?1 offset ?2", nativeQuery = true)
    List<Book> findWithLimitWithOffset(Integer limit, Integer offset);
}
