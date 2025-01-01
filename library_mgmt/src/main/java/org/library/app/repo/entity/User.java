package org.library.app.repo.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static Integer count = 1;
    private final Integer bookLimit;
    private final Integer userId;
    private final List<Book> bookIssuedList;
    private final List<Book> bookReturnedList;

    public User() {
        this.userId = count++;
        this.bookIssuedList = new ArrayList<>();
        this.bookReturnedList = new ArrayList<>();
        this.bookLimit = 5;
    }

    public Integer getBookLimit() {
        return bookLimit;
    }

    public Integer getUserId() {
        return userId;
    }

    public List<Book> getBookIssuedList() {
        return bookIssuedList;
    }

    public List<Book> getBookReturnedList() {
        return bookReturnedList;
    }
}
