package com.sjtu.rbj.bookstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.sjtu.rbj.bookstore.dto.BookDTO;
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
@CrossOrigin(Constants.ALLOW_ORIGIN)
public class BookController {

    @GetMapping
    public String getBookListForHomePage(@RequestParam(defaultValue = "4") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset) {
        List<Book> bookList = bookService.getBookDataListForHomePage(limit, offset);
        List<BookDTO> bookDataList = new ArrayList<>();
        for (Book book : bookList) {
            bookDataList.add(BookDTO.from(book));
        }
        JSONArray jsonArray = new JSONArray(bookDataList);
        return jsonArray.toString();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getBook(@PathVariable("uuid") UUID uuid) {
        try {
            Book book = bookService.getBookByUuid(uuid);
            return ResponseEntity.ok().body(JSON.toJSON(BookDTO.from(book)));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Autowired
    private BookService bookService;

}
