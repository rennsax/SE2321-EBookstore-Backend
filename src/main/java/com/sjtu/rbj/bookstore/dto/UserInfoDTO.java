package com.sjtu.rbj.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Necessary user info for common user who interact with the bookstore.
 * DTO for {@link com.sjtu.rbj.bookstore.entity.User}
 *
 * @author Bojun Ren
 * @data 2023/04/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    /** aka. the primary key */
    private Integer id;

    /** Current order with statue "pending" */
    private Integer orderId;

    private String name;
    private String avatarId;
}