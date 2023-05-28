package com.sjtu.rbj.bookstore.dto;

import java.util.UUID;

import com.sjtu.rbj.bookstore.entity.Book;
import com.sjtu.rbj.bookstore.entity.Order.OrderItem;
import com.sjtu.rbj.bookstore.utils.PriceHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bojun Ren
 * @data 2023/04/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOrderedDTO {
    private UUID uuid;
    private Integer quantity;
    private String totalBudget;

    static BookOrderedDTO from(OrderItem orderItem) {
        Book book = orderItem.getBook();
        Integer quantity = orderItem.getQuantity();
        return new BookOrderedDTO(book.getUuid(), quantity,
                PriceHandler.from(quantity * book.getPriceCent()).toString());
    }
}
