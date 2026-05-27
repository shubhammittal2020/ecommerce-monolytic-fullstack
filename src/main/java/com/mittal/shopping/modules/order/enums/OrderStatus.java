package com.mittal.shopping.modules.order.enums;

public enum OrderStatus {

    PLACED,             // order created
    CONFIRMED,          // payment successful/accepted
    PROCESSING,         // warehouse preparing order
    SHIPPED,            // dispatched
    DELIVERED,          // customer received order
    CANCELLED,          // order cancelled
    RETURNED            // returned after delivery

}
