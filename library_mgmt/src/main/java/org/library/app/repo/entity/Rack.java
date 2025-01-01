package org.library.app.repo.entity;

public class Rack {
    private final Integer rackNumber;
    private Book book;

    public Rack(Integer rackNumber) {
        this.rackNumber = rackNumber;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getRackNumber() {
        return rackNumber;
    }
}
