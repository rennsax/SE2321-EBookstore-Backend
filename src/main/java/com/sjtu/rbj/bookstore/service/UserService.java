package com.sjtu.rbj.bookstore.service;

import java.util.NoSuchElementException;

import com.sjtu.rbj.bookstore.data.UserInfo;

/**
 * @author Bojun Ren
 * @date 2023/04/18
 */
public interface UserService {
    /**
     * verify account and password
     * @param account
     * @param passwd
     * @return {@code true} on success
     */
    boolean enableLogin(String account, String passwd);

    /**
     * get information {userId, orderId} by user's account
     * @param account user's account, unique
     * @return UserInfo {userId, orderId}
     * @throws NoSuchElementException if no such user
     */
    UserInfo getUserInfoByAccount(String account);
}
