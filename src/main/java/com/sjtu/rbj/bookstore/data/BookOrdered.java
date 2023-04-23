package com.sjtu.rbj.bookstore.data;

import java.util.UUID;

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
public class BookOrdered {
    private UUID uuid;
    private Integer quantity;
}
