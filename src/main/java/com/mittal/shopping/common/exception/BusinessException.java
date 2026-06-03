package com.mittal.shopping.common.exception;

public class BusinessException extends RuntimeException {

    /*
    Cart empty
    Out of stock
    Invalid quantity
    Order already paid
    */

    public BusinessException(String message) {
        super(message);
    }

}
