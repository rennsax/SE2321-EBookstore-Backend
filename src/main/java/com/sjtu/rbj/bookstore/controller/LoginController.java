package com.sjtu.rbj.bookstore.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.sjtu.rbj.bookstore.constant.Constant;
import com.sjtu.rbj.bookstore.service.UserService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Bojun Ren
 * @date 2023/04/08
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("/login")
    public String getUser(@RequestBody Map<String, String> params) {
        String account = params.get(Constant.ACCOUNT);
        String passwd = params.get(Constant.PASSWORD);
        log.info(account);
        log.info(passwd);
        JSONObject res = new JSONObject();
        if (account == null || passwd == null || !userService.enableLogin(account, passwd)) {
            res.put("flag", false);
        } else {
            res.put("flag", true);
        }
        return res.toString();
    }
}
