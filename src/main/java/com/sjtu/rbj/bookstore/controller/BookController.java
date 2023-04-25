package com.sjtu.rbj.bookstore.controller;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sjtu.rbj.bookstore.constant.Constant;
import com.sjtu.rbj.bookstore.entity.Book;
import com.sjtu.rbj.bookstore.service.BookService;

import lombok.extern.slf4j.Slf4j;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no such book")
class NoSuchBookException extends NoSuchElementException {
}

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
    public String getBookListForHomePage(@RequestParam(defaultValue = "4") Integer limit, @RequestParam(defaultValue = "0") Integer offset) {
        JSONArray res = new JSONArray(bookService.getBookListForHomePage(limit, offset));
        log.info(Integer.toString(limit), Integer.toString(offset));
        return res.toString();
    }

    @CrossOrigin(Constant.ALLOW_ORIGIN)
    @GetMapping("/{uuid}")
    public String getBook(@PathVariable("uuid") UUID uuid) {
        log.info(uuid.toString());
        Optional<Book> res = bookService.getBookByUuid(uuid);
        if (!res.isPresent()) {
            throw new NoSuchBookException();
        }
        return JSONObject.toJSONString(res.get());
    }
}
