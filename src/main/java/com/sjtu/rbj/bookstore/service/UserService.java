package com.sjtu.rbj.bookstore.service;

import com.sjtu.rbj.bookstore.data.UserInfo;

/**
 * @author Bojun Ren
 * @date 2023/04/18
 */
public interface UserService {
    boolean enableLogin(String account, String passwd);
    /**
     * get necessary information by user's id
     * @param userId user's PK
     * @return UserInfo {userId, orderId}
     */
    /**UserInfo getUserInfoById(Integer userId);*/

    /**
     * get necessary information by user's account
     * @param account user's account, unique
     * @return UserInfo {userId, orderId}
     */
    UserInfo getUserInfoByAccount(String account);
}
