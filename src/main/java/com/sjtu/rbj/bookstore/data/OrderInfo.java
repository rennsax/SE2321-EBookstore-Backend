package com.sjtu.rbj.bookstore.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sjtu.rbj.bookstore.constant.OrderState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bojun Ren
 * @data 2023/04/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {
    private Integer id;
    private OrderState state;
    private Timestamp time;
    private String sumBudget;
    private List<BookOrdered> bookOrderedList = new ArrayList<>();
}
