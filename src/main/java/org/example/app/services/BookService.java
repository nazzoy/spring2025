package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(BookRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        if (!isEmpty(book)) {
            bookRepo.store(book);
        }
    }

    private boolean isEmpty(Book book) {
        return (book.getAuthor() == null || book.getAuthor().trim().isEmpty())
                && (book.getTitle() == null || book.getTitle().trim().isEmpty())
                ;
    }

    public boolean removeBookById(String bookIdToRemove) {
        return bookRepo.removeItemById(String.valueOf(bookIdToRemove));
    }

    public int removeBooksByRegex(String regex) {
        Pattern pattern = Pattern.compile(regex);
        List<Book> booksToRemove = bookRepo.retreiveAll().stream()
                .filter(book -> pattern.matcher(book.getAuthor()).find()
                        || pattern.matcher(book.getTitle()).find()
                        || pattern.matcher(String.valueOf(book.getSize())).find())
                .collect(Collectors.toList());

        booksToRemove.forEach(book -> bookRepo.removeItemById(String.valueOf(book.getId())));
        return booksToRemove.size();
    }

}
