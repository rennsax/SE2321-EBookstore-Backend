package com.sjtu.rbj.bookstore.dao;

import java.util.List;
import java.util.Optional;

import com.sjtu.rbj.bookstore.entity.User;

/**
 * @author Bojun Ren
 * @date 2023/04/18
 */
public interface UserDao {
    /**
     * find a user by account
     *
     * @param account
     * @return if exist, the user entity is presented.
     */
    Optional<User> findByAccount(String account);
    /**
     * find a user by account and password, used to verify authority
     *
     * @param account
     * @param passwd
     * @return List of User
     */
    List<User> findByAccountAndPasswd(String account, String passwd);
}
