package com.sjtu.rbj.bookstore.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sjtu.rbj.bookstore.constant.Constant;
import com.sjtu.rbj.bookstore.entity.Book;
import com.sjtu.rbj.bookstore.service.BookService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Bojun Ren
 * @date 2023/04/18
 */
@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @CrossOrigin(Constant.ALLOW_ORIGIN)
    @GetMapping
    public String listBooks(@RequestParam(defaultValue = "4") Integer limit) {
        JSONArray res = new JSONArray(bookService.listBooks(limit));
        return res.toString();
    }

    @CrossOrigin(Constant.ALLOW_ORIGIN)
    @GetMapping("/{uuid}")
    public String getBook(@PathVariable("uuid") UUID uuid) {
        log.info(uuid.toString());
        Optional<Book> res = bookService.getBookByUuid(uuid);
        if (!res.isPresent()) {
            return "null";
        }
        return JSONObject.toJSONString(res.get());
    }
}
