package com.sjtu.rbj.bookstore.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.rbj.bookstore.constant.Constant;



/**
 * @author Bojun Ren
 * @data 2023/04/23
 */
@RestController
@CrossOrigin(Constant.ALLOW_ORIGIN)
public class UserController {

    @GetMapping(value="/{userId}")
    public String getUserInfo(@PathVariable Integer userId) {
        return "hello";
    }
}
