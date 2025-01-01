package org.library.app.repo;

import org.library.app.repo.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private final List<Book> books;

    public BookRepository(){
        this.books = new ArrayList<>();
    }

    public void save(Book book){
        this.books.add(book);
    }

    public Book findByBookCopyId(String bookCopyId){
        return this.books.stream()
                .filter(book -> book.getBookCopyId().equals(bookCopyId))
                .findFirst()
                .orElse(null);
    }

    public List<Book> findByBookId(Integer bookId){
        return this.books.stream()
                .filter(book -> book.getBookId().equals(bookId))
                .toList();
    }

    public Book findByTitle(String title){
        return this.books.stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    public List<Book> findAllByAuthor(String authorName){
        return this.books.stream()
                .filter(book -> {
                    for(String author: book.getAuthors()){
                        if(author.equals(authorName)) return true;
                    }
                    return false;
                }).toList();
    }

    public List<Book> findAllByPublisher(String publisherName){
        return this.books.stream()
                .filter(book -> {
                    for(String publisher: book.getPublishers()){
                        if(publisher.equals(publisherName)) return true;
                    }
                    return false;
                })
                .toList();
    }
}
