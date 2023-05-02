package com.sjtu.rbj.bookstore.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.sjtu.rbj.bookstore.constant.Constants;
import com.sjtu.rbj.bookstore.data.UserInfo;
import com.sjtu.rbj.bookstore.service.UserService;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such user!")
class NoSuchUserException extends NoSuchElementException {
}


/**
 * @author Bojun Ren
 * @data 2023/04/23
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(Constants.ALLOW_ORIGIN)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getUserInfo(@RequestParam(value = "account", defaultValue = "") String account) {
        try {
            UserInfo userInfo = userService.getUserInfoByAccount(account);
            return JSON.toJSONString(userInfo);
        } catch (Exception e) {
            throw new NoSuchUserException();
        }
    }
}
