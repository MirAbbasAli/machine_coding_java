package org.library.app;

import org.library.app.service.LibraryService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LibraryMgmtApplication {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String invalidInput = "Invalid input, try again";
        LibraryService libraryService = new LibraryService();
        boolean running = true;
        int userId, bookId;
        String bookCopyId;
        while(running){
            try{
                String userInput = in.nextLine();
                if(userInput.equals("exit")){
                    running = false;
                    continue;
                }
                List<String> parts = Arrays.stream(userInput.split(" ")).toList();
                if(parts.isEmpty()){
                    System.out.println(invalidInput);
                    continue;
                }
                switch(parts.get(0)){
                    case "create_library":
                        libraryService.createLibrary(Integer.parseInt(parts.get(1)));
                        break;
                    case "add_book":
                        bookId = Integer.parseInt(parts.get(1));
                        String title  = parts.get(2);
                        List<String> authors = Arrays.stream(parts.get(3).split(",")).toList();
                        List<String> publishers = Arrays.stream(parts.get(4).split(",")).toList();
                        List<String> bookCopies = Arrays.stream(parts.get(5).split(",")).toList();
                        libraryService.addBooks(bookId, bookCopies, title, authors, publishers);
                        break;
                    case "remove_book_copy":
                        bookCopyId = parts.get(1);
                        libraryService.removeBookCopy(bookCopyId);
                        break;
                    case "borrow_book":
                        userId = Integer.parseInt(parts.get(1));
                        if(parts.get(2).contains("book")){
                            bookCopyId = parts.get(2);
                            libraryService.borrowBookCopy(userId, bookCopyId);
                        } else {
                            bookId = Integer.parseInt(parts.get(2));
                            libraryService.borrowBook(userId, bookId);
                        }
                        break;
                    case "return_book_copy":
                        bookCopyId = parts.get(1);
                        libraryService.returnBookCopy(bookCopyId);
                        break;
                    case "print_borrowed":
                        userId = Integer.parseInt(parts.get(1));
                        libraryService.printBooksBorrowed(userId);
                        break;
                    case "print_returned":
                        userId = Integer.parseInt(parts.get(1));
                        libraryService.printBooksReturned(userId);
                        break;
                    case "search":
                        switch(parts.get(1)){
                            case "book_id":
                                bookId = Integer.parseInt(parts.get(2));
                                libraryService.searchByBookId(bookId);
                                break;
                            case "title":
                                libraryService.searchByTitle(parts.get(2));
                                break;
                            case "author_id":
                                libraryService.searchByAuthor(parts.get(2));
                                break;
                            case "publisher_id":
                                libraryService.searchByPublisher(parts.get(2));
                                break;
                            default:
                                System.out.println(invalidInput);
                                break;
                        }
                        break;
                    default:
                        System.out.println(invalidInput);
                        break;
                }

            } catch(Exception e){
                System.out.println(invalidInput);
            }
        }
    }
}
