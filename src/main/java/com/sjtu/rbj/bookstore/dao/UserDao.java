package com.sjtu.rbj.bookstore.dao;

import java.util.Optional;

import com.sjtu.rbj.bookstore.entity.User;

/**
 * @author Bojun Ren
 * @date 2023/04/18
 */
public interface UserDao {
    /**
     * Retrieves a user entity by its account (defined as unique key).
     *
     * @param account must not be {@literal null}.
     * @return the entity with the given account or {@literal Optional#empty()} if none found.
	 * @throws IllegalArgumentException if {@literal account} is {@literal null}.
     */
    Optional<User> findByAccount(String account);

    /**
     * Retrieves a user entity by its account (defined as unique key) and passwd.
     *
     * @param account must not be {@literal null}.
     * @param passwd must not be {@literal null}.
     * @return the entity with the given account or {@literal Optional#empty()} if none found.
	 * @throws IllegalArgumentException if either {@literal account} or {@literal passwd} is {@literal null}.
     */
    Optional<User> findByAccountAndPasswd(String account, String passwd);

	/**
	 * Flushes all pending changes to the database.
	 */
	void flush();
}
