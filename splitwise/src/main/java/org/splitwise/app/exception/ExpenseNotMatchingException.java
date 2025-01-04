package org.splitwise.app.exception;

public class ExpenseNotMatchingException extends RuntimeException {
    public ExpenseNotMatchingException(String message) {
        super(message);
    }
}
