package io.order.app.exception;

public class InvalidUserException extends IllegalArgumentException{
    public InvalidUserException(String s) {
        super(s);
    }
}
