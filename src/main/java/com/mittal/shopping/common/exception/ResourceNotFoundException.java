package com.mittal.shopping.common.exception;

public class ResourceNotFoundException extends RuntimeException {

    /*
    User not found
    Product not found
    Cart not found
    Order not found
    */

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
