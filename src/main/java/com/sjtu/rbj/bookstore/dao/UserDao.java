package com.sjtu.rbj.bookstore.dao;

import java.util.Optional;

import com.sjtu.rbj.bookstore.entity.User;

/**
 * @author Bojun Ren
 * @date 2023/04/18
 */
public interface UserDao {
    /**
     * find user by account
     * @param account the user's account
     * @return {@code Optional<User>}
     */
    Optional<User> findByAccount(String account);
    /**
     * find a user by account and password, used to verify authority
     * @param account
     * @param passwd
     * @return {@code Optional<User>}
     */
    Optional<User> findByAccountAndPasswd(String account, String passwd);
}
