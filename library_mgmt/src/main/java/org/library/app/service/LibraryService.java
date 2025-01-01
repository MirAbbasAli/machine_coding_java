package org.library.app.service;

import org.library.app.repo.BookRepository;
import org.library.app.repo.LibraryRepository;
import org.library.app.repo.UserRepository;
import org.library.app.repo.entity.Book;
import org.library.app.repo.entity.BookStatus;

import java.util.List;
import java.util.stream.Collectors;

public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final UserService userService;
    private final BookRepository bookRepository;

    public LibraryService(){
        this.libraryRepository = new LibraryRepository();
        this.bookRepository = new BookRepository();
        this.userService = new UserService(new UserRepository(), this.bookRepository);
    }

    public void createLibrary(Integer numOfRacks){
        this.libraryRepository.save(numOfRacks);
        System.out.printf("Created library with %d racks%n", numOfRacks);
    }

    public void addBooks(Integer bookId, List<String> bookCopies, String title, List<String> authors, List<String> publishers){
        if(bookCopies.size() > this.libraryRepository.getAvailableRacks()){
            System.out.println("Rack not available");
            return;
        }
        String rackAssigned = bookCopies.stream()
                .map( copyId -> {
                    Book book = new Book(bookId, copyId, title, authors, publishers);
                    this.bookRepository.save(book);
                    return this.libraryRepository.addToRack(book).toString();
                }).collect(Collectors.joining(" "));

        System.out.printf("Added Book to racks: %s%n", rackAssigned);
    }

    public void borrowBook(Integer userId, Integer bookId){
        Book borrowedBook = this.userService.borrowBook(userId, bookId);
        if(borrowedBook != null){
            System.out.printf("Borrowed Book Copy from rack: %d%n", this.libraryRepository.removeBookFromRack(borrowedBook));
        }
    }

    public void borrowBookCopy(Integer userId, String bookCopyId){
        Book borrowedBook = this.userService.borrowBookCopy(userId, bookCopyId);
        if(borrowedBook != null){
            System.out.printf("Borrowed Book Copy from rack: %d%n", this.libraryRepository.removeBookFromRack(borrowedBook));
        }
    }

    public void removeBookCopy(String bookCopyId){
        Book book = this.bookRepository.findByBookCopyId(bookCopyId);
        if(book == null){
            System.out.printf("Invalid book copy id %s%n", bookCopyId);
            return;
        }
        book.setBookStatus(BookStatus.REMOVED);
        Integer rackNum = this.libraryRepository.removeBookFromRack(book);
        if(rackNum != -1){
            System.out.printf("Removed book: %s from rack: %d%n", bookCopyId, rackNum);
        }
    }

    public void returnBookCopy(String bookId){
        var book = this.userService.returnBookCopy(bookId);
        if(book != null){
            book.setBookStatus(BookStatus.AVAILABLE);
            Integer rackNumber = this.libraryRepository.addToRack(book);
            System.out.printf("Returned book copy %s and added to rack: %d%n", bookId, rackNumber);
        }
    }

    public void printBooksBorrowed(Integer userId){
        this.userService.printBooksBorrowed(userId);
    }

    public void printBooksReturned(Integer userId){
        this.userService.printBooksReturned(userId);
    }

    public void searchByBookId(Integer bookId){
        List<Book> books = this.bookRepository.findByBookId(bookId);
        if(books.isEmpty()){
            return;
        }
        books.forEach(this::displayBookInfo);
    }

    public void searchByTitle(String title){
        Book book = this.bookRepository.findByTitle(title);
        if(book == null){
            return;
        }
        displayBookInfo(book);
    }

    public void searchByAuthor(String authorName){
        List<Book> books = this.bookRepository.findAllByAuthor(authorName);
        if(books.isEmpty()){
            return;
        }
        books.forEach(this::displayBookInfo);
    }

    public void searchByPublisher(String publisherName){
        List<Book> books = this.bookRepository.findAllByPublisher(publisherName);
        if(books.isEmpty()){
            return;
        }
        books.forEach(this::displayBookInfo);
    }

    private void displayBookInfo(Book book){
        String authors = String.join(",", book.getAuthors());
        String publishers = String.join(",", book.getPublishers());

        System.out.printf("Book: Book ID: %s, Title: %s, Authors: %s, Publishers: %s, Status: %s, rackNumber: %d", book.getBookId(), book.getTitle(), authors, publishers, book.getBookStatus(), book.getRackNumber());
        if(book.getBookStatus().equals(BookStatus.ISSUED)){
            System.out.printf(", Issued to: %d, Issue Date: %s, Due Date: %s%n", book.getUserId(), book.getIssueDate(), book.getDueDate());
        } else {
            System.out.println();
        }
    }
}
