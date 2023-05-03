package com.sjtu.rbj.bookstore.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bojun Ren
 * @data 2023/04/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private Integer id;
    /**
     * Current order with statue "pending"
     */
    private Integer orderId;

    private String name;
    private String avatarId;
}