package org.inventory.app.exception;


public class OrderStatusException extends IllegalArgumentException{
    public OrderStatusException(String message) {
        super(message);
    }
}
