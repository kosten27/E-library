package com.kostenko.elibrary.controllers;

import com.kostenko.elibrary.models.Book;
import com.kostenko.elibrary.services.AuthorService;
import com.kostenko.elibrary.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class BookController {

    private BookService bookService;
    private AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/books")
    public String getBooks(Model model,
                           @RequestParam(name = "page", defaultValue = "1") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "search", defaultValue = "") String search) {
        Page<Book> books;
        PageRequest pageable = PageRequest.of(page - 1, size);
        if (search.equals("")) {
            books = bookService.findAll(pageable);
        } else {
            books = bookService.findByTitleIsContaining(search, pageable);
        }
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/books/add")
    public String showAddBook(Book book, Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "books/book";
    }

    @PostMapping("/books/save")
    public String saveBook(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "books/book";
        }
        bookService.save(book);
        return "redirect:/books/" + book.getId();
    }

    @GetMapping("/books/{id}")
    public String showUpdateBook(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        return "books/book";
    }

    @GetMapping("books/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }
}
