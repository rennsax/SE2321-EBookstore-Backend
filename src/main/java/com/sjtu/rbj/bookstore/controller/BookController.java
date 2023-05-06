package com.sjtu.rbj.bookstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.sjtu.rbj.bookstore.constant.Constants;
import com.sjtu.rbj.bookstore.data.BookData;
import com.sjtu.rbj.bookstore.entity.Book;
import com.sjtu.rbj.bookstore.service.BookService;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no such book")
class NoSuchBookException extends NoSuchElementException {
}

/**
 * @author Bojun Ren
 * @date 2023/04/18
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @CrossOrigin(Constants.ALLOW_ORIGIN)
    @GetMapping
    public String getBookListForHomePage(@RequestParam(defaultValue = "4") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset) {
        List<Book> bookList = bookService.getBookDataListForHomePage(limit, offset);
        List<BookData> bookDataList = new ArrayList<>();
        for (Book book : bookList) {
            bookDataList.add(BookData.of(book));
        }
        JSONArray jsonArray = new JSONArray(bookDataList);
        return jsonArray.toString();
    }

    @CrossOrigin(Constants.ALLOW_ORIGIN)
    @GetMapping("/{uuid}")
    public String getBook(@PathVariable("uuid") UUID uuid) {
        Book book = bookService.getBookByUuid(uuid);
        return JSON.toJSONString(BookData.of(book));
    }
}
