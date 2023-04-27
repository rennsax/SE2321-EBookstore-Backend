package com.sjtu.rbj.bookstore.dao;

import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.ast.Or;

import com.sjtu.rbj.bookstore.entity.Order;

/**
 * @author Bojun Ren
 * @data 2023/04/23
 */
public interface OrderDao {
    /**
     * save an entity to the database
     * @param entity
     * @return the saved entity
     * @throws IllegalArgumentException
     * @throws OptimisticLockingFailureException
     */
    <S extends Order> S save(S entity);

    /**
     * find all order of an user
     *
     * @param userId
     * @return
     */
    List<Order> findByUserId(Integer userId);

	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none found.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
    Optional<Order> findById(Integer id);
}
