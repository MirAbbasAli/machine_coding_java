package org.library.app.repo.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Book {
    private final Integer bookId;
    private final String bookCopyId;
    private final String title;
    private final List<String> authors;
    private final List<String> publishers;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BookStatus bookStatus;
    private Integer rackNumber;
    private Integer userId;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Book(Integer bookId, String bookCopyId, String title, List<String> authors, List<String> publishers) {
        this.bookId = bookId;
        this.bookCopyId = bookCopyId;
        this.title = title;
        this.authors = authors;
        this.publishers = publishers;
        this.bookStatus = BookStatus.AVAILABLE;
        this.issueDate = LocalDate.now();
        this.dueDate = issueDate.plusDays(7);
        this.rackNumber = -1;
        this.userId = -1;

    }

    public String getBookCopyId() {
        return bookCopyId;
    }

    public Integer getRackNumber() {
        return rackNumber;
    }

    public void setRackNumber(Integer rackNumber) {
        this.rackNumber = rackNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getBookId() {
        return bookId;
    }


    public List<String> getAuthors() {
        return authors;
    }


    public List<String> getPublishers() {
        return publishers;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(!(obj instanceof  Book book)) return false;
        return getBookCopyId() != null && this.getBookCopyId().equals(book.getBookCopyId());
    }

    @Override
    public String toString() {
        return String.format("Book - id: %d, copy_id: %s, title: %s, issue date: %s, due date: %s", bookId, bookCopyId, title, issueDate.format(format), dueDate.format(format));
    }
}
