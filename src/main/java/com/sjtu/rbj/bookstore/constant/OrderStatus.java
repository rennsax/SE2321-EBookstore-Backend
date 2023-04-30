package com.sjtu.rbj.bookstore.constant;

/**
 * @author Bojun Ren
 * @data 2023/04/23
 */
public enum OrderStatus {
    /**
     * An order that is pending, typically denotes the cart.
     */
    PENDING,

    /**
     * An order that is already complete.
     */
    COMPLETE,

    /**
     * An order that is submitted, but yet not complete.
     */
    TRANSPORTING;

}
