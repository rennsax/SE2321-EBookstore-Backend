package com.sjtu.rbj.bookstore.constant;

/**
 * @author Bojun Ren
 * @data 2023/04/23
 */
public enum UserType {
    /**
     * Normal user
     */
    NORMAL("normal"),

    /**
     * super user
     */
    SUPER("super");

    private UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    private String type;
}
