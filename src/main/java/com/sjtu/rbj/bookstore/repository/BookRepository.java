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
public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * Retrieves a book entity by its uuid (defined as unique key).
     *
     * @param uuid must not be {@literal null}.
     * @return the entity with the given uuid or {@literal Optional#empty()} if none found.
	 * @throws IllegalArgumentException if {@literal uuid} is {@literal null}.
     */
    Optional<Book> findByUuid(UUID uuid);

    /**
     * Retrieves book entities, with maximum entity number and a certain offset.
     *
     * @param limit the maximum entity number, must not be {@literal null}.
     * @param offset must not be {@literal null}.
     * @return available entities no more than {@literal limit}.
	 * @throws IllegalArgumentException if either {@literal limit} or {@literal offset} is {@literal null}.
     */
    @Query(value = "select * from `book` limit ?1 offset ?2", nativeQuery = true)
    List<Book> findWithLimitWithOffset(Integer limit, Integer offset);
}
