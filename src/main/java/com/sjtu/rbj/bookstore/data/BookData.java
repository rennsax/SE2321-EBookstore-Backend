package com.sjtu.rbj.bookstore.data;

import java.sql.Date;
import java.util.UUID;

import com.sjtu.rbj.bookstore.entity.Book;
import com.sjtu.rbj.bookstore.utils.PriceHandler;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Bojun Ren
 * @data 2023/05/07
 */
@Getter
@ToString
public class BookData {
    private UUID uuid;
    private String title;
    private String picId;
    private String price;
    private String author;
    private Date date;
    private String isbn;
    private String description;

    /**
     * Package the book entity into book data.
     * @param book
     * @return book data which can be returned to front-end.
     */
    public static BookData of(Book book) {
        return new BookData(book.getUuid(),
                book.getTitle(), book.getPicId(),
                PriceHandler.of(book.getPriceCent()).toString(),
                book.getAuthor(), book.getDate(), book.getIsbn(), book.getDescription());
    }

    public BookData(UUID uuid, String title, String picId, String price, String author, Date date, String isbn,
            String description) {
        this.uuid = uuid;
        this.title = title;
        this.picId = picId;
        this.price = price;
        this.author = author;
        this.date = date;
        this.isbn = isbn;
        this.description = description;
    }
}