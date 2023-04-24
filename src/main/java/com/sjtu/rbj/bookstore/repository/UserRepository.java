package com.sjtu.rbj.bookstore.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sjtu.rbj.bookstore.entity.User;

/**
 * @author Bojun Ren
 * @date 2023/04/17
 */
public interface UserRepository extends JpaRepository<User, Integer> {
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
