package com.sjtu.rbj.bookstore.utils;

/**
 * An util class to handle the price.
 *
 * @author Bojun Ren
 * @data 2023/05/07
 */
public class PriceHandler {

    private Integer price;
    private Integer decimalPlace = 2;

    /**
     * Create from the price only. The default decimal place is 2.
     * @param price
     */
    public PriceHandler(Integer price) {
        this.price = price;
    }

    public PriceHandler(Integer price, Integer decimalPlace) {
        this.price = price;
        this.decimalPlace = decimalPlace;
    }

    @Override
    public String toString() {
        String priceStr = price.toString();
        int integerPlace = priceStr.length() - decimalPlace;
        return priceStr.substring(0, integerPlace) + "." + priceStr.substring(integerPlace);
    }
}
