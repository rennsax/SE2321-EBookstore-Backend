package com.sjtu.rbj.bookstore.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.rbj.bookstore.constant.Constant;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bojun Ren
 * @date 2023/04/19
 */
@Slf4j
@RestController
@RequestMapping("/order")
@CrossOrigin(Constant.ALLOW_ORIGIN)
public class OrderController {

    @PostMapping("/submit")
    public String submitOrder(@RequestBody Map<String, String> params) {
        // TODO
        return "";
    }

    // TODO
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemFromOrder() {
    }

}
