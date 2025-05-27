package org.example.web.controllers;


import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "books")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }
        // Проверяем, что хотя бы одно поле не пустое
//        if (isEmpty(book)) {
//            model.addAttribute("error", "Please fill in at least one field!");
//            return "redirect:/books/shelf"; // Возвращаем на страницу с ошибкой
//        }
//
//        // Сохраняем книгу, если проверка прошла успешно
//        bookService.saveBook(book);
//        return "redirect:/books/shelf"; // Перенаправляем на страницу с книгами
//    }

    // Метод для проверки, что хотя бы одно поле заполнено
//    private boolean isEmpty(Book book) {
//        return (book.getAuthor() == null || book.getAuthor().trim().isEmpty())
//                && (book.getTitle() == null || book.getTitle().trim().isEmpty())
//                && (book.getSize() == null || book.getSize().trim().isEmpty());
//    }


//    @PostMapping("/remove")
//    public String removeBook(@RequestParam(value = "bookIdToRemove") Integer bookIdToRemove) {
//        if (bookService.removeBookById(bookIdToRemove)) {
//            // Если книга удалена, перенаправляем на ту же страницу
//            return "redirect:/books/shelf";
//        } else {
//            // Возвращаемся на страницу книги
//            return "redirect:/books/shelf";
//        }
//    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookById(bookIdToRemove.getId());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByRegex")
    public String removeBooksByRegex(@RequestParam("queryRegex") String queryRegex, Model model) {
        if (queryRegex == null || queryRegex.trim().isEmpty()) {
            model.addAttribute("error", "Regex cannot be empty.");
            return "book_shelf";
        }

        int removedCount = bookService.removeBooksByRegex(queryRegex);

        model.addAttribute("message", removedCount + " book(s) removed matching the regex.");
        return "redirect:/books/shelf";
    }


}
