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
     * Retrieves book entities, with maximum entity number and a certain offset.
     *
     * @param limit the maximum entity number, must not be {@literal null}.
     * @param offset must not be {@literal null}.
     * @return available entities no more than {@literal limit}.
	 * @throws IllegalArgumentException if either {@literal limit} or {@literal offset} is {@literal null}.
     */
    List<Book> findWithLimitWithOffset(Integer limit, Integer offset);

    /**
     * Retrieves a book entity by its uuid (defined as unique key).
     *
     * @param uuid must not be {@literal null}.
     * @return the entity with the given uuid or {@literal Optional#empty()} if none found.
	 * @throws IllegalArgumentException if {@literal uuid} is {@literal null}.
     */
    Optional<Book> findByUuid(UUID uuid);

	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none found.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
    Optional<Book> findById(Integer id);

    /**
	 * Returns all instances of books.
	 *
	 * @return all entities
	 */
    List<Book> findAll();


    /**
     * Delete a book by its uuid.
     * @param uuid
     */
    void deleteByUuid(UUID uuid);

    /**
     * Search by the book title.
     * @param like
     * @return results.
     */
    List<Book> findByTitleLike(String like);


    /**
     * Save a book entity.
     * @param <S>
     * @param entity
     * @return the managed entity.
     */
    <S extends Book> S save(S entity);
}
