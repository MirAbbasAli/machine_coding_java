package org.library.app.service;

import org.library.app.repo.BookRepository;
import org.library.app.repo.UserRepository;
import org.library.app.repo.entity.Book;
import org.library.app.repo.entity.BookStatus;
import org.library.app.repo.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public UserService(UserRepository userRepository, BookRepository bookRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public Book borrowBookCopy(Integer userId, String bookCopyId){
        Book book = this.bookRepository.findByBookCopyId(bookCopyId);
        if(book==null){
            System.out.printf("Invalid book copy id %s%n", bookCopyId);
            return null;
        }
        if(book.getBookStatus().equals(BookStatus.AVAILABLE)){
            User user = this.findOrCreateUser(userId);
            if(user.getBookIssuedList().size() < user.getBookLimit()){
                book.setBookStatus(BookStatus.ISSUED);
                book.setUserId(userId);
                user.getBookIssuedList().add(book);
                return book;
            }
            else{
                System.out.println("Overlimit");
                return null;
            }
        }
        else{
            System.out.println("Book not available");
            return null;
        }
    }


    public Book borrowBook(Integer userId, Integer bookId){
        List<Book> books = this.bookRepository.findByBookId(bookId);
        if(books.isEmpty()){
            System.out.println("Invalid Book Copy ID");
            return null;
        }
        Optional<Book> book = books.stream()
                .filter(b -> b.getBookStatus().equals(BookStatus.AVAILABLE))
                .findFirst();
        if(book.isEmpty()){
            System.out.println("Not available");
            return null;
        }
        User user = this.findOrCreateUser(userId);
        Book bookCopy = book.get();

        if(user.getBookIssuedList().size() < user.getBookLimit()){
            bookCopy.setBookStatus(BookStatus.ISSUED);
            bookCopy.setIssueDate(LocalDate.now());
            bookCopy.setDueDate(LocalDate.now().plusDays(7));
            bookCopy.setUserId(userId);
            user.getBookIssuedList().add(bookCopy);
            return bookCopy;
        }
        else {
            System.out.println("Overlimit");
            return null;
        }
    }

    public Book returnBookCopy(String bookCopyId) {
        Book book = this.bookRepository.findByBookCopyId(bookCopyId);
        if (book == null) {
            System.out.printf("Invalid book id %s%n", bookCopyId);
            return null;
        }

        User user = this.userRepository.findByUserId(book.getUserId());
        if (user == null) {
            System.out.println("No such user exists");
            return null;
        }
        book.setUserId(-1);
        book.setDueDate(null);
        book.setIssueDate(null);
        user.getBookIssuedList().remove(book);
        user.getBookReturnedList().add(book);
        return book;
    }

    private User findOrCreateUser(Integer userId){
        User user = this.userRepository.findByUserId(userId);
        if(user == null){
            User newUser = new User();
            user = this.userRepository.save(newUser);
        }
        return user;
    }


    public void printBooksBorrowed(Integer userId){
        User user = this.userRepository.findByUserId(userId);
        if(user != null){
            user.getBookIssuedList().forEach(System.out::println);
        }
    }

    public void printBooksReturned(Integer userId){
        User user = this.userRepository.findByUserId(userId);
        if(user != null){
            user.getBookReturnedList().forEach(System.out::println);
        }
    }
}
