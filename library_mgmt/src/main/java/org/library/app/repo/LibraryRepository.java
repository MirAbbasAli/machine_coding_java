package org.library.app.repo;

import org.library.app.repo.entity.Book;
import org.library.app.repo.entity.Library;
import org.library.app.repo.entity.Rack;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class LibraryRepository {
    private final Library library;


    public LibraryRepository(){
        library = new Library();
    }

    public void save(Integer numOfRacks){
        List<Rack> racks = IntStream.range(0, numOfRacks)
                .mapToObj(Rack::new)
                .toList();
        this.library.setRacks(racks);
        this.library.setAvailableRacks(numOfRacks);
    }

    public Integer removeBookFromRack(Book book){
        Optional<Rack> optionalRack = this.library.getRacks()
                .stream()
                .filter(rack -> rack.getBook()!=null && rack.getBook().equals(book))
                .findFirst();
        if(optionalRack.isPresent()){
            var rack = optionalRack.get();
            book.setRackNumber(-1);
            rack.setBook(null);
            this.library.setAvailableRacks(this.library.getAvailableRacks() + 1);
            return rack.getRackNumber();
        }
        return -1;
    }

    public Integer addToRack(Book book){
        Optional<Rack> optionalRack = this.library.getRacks()
                .stream()
                .filter(rack -> rack.getBook() == null)
                .findFirst();
        if(optionalRack.isPresent()){
            var rack = optionalRack.get();
            book.setRackNumber(rack.getRackNumber());
            rack.setBook(book);
            this.library.setAvailableRacks(this.library.getAvailableRacks() - 1);
            return rack.getRackNumber();
        }
        return -1;
    }

    public Library getLibrary(){
        return this.library;
    }

    public Integer getAvailableRacks(){
        return this.library.getAvailableRacks();
    }

}
