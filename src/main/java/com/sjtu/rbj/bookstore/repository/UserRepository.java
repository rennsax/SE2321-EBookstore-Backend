package com.sjtu.rbj.bookstore.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sjtu.rbj.bookstore.entity.User;

/**
 * @author Bojun Ren
 * @date 2023/04/17
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByAccount(String account);
    List<User> findByAccountAndPasswd(String account, String passwd);
}
