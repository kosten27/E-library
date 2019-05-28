package com.kostenko.elibrary.controllers;

import com.kostenko.elibrary.models.Book;
import com.kostenko.elibrary.services.AuthorService;
import com.kostenko.elibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    @GetMapping("/books")
    public String getBooks(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("search") Optional<String> search) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        String stringSearch = search.orElse("");
        Page<Book> books;
        PageRequest pageable = PageRequest.of(currentPage - 1, pageSize);
        if (stringSearch.equals("")) {
            books = bookService.findAll(pageable);
        } else {
            books = bookService.findByTitleIsContaining(stringSearch, pageable);
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
//        model.addAttribute("series", book.getSeries());
        return "books/book";
    }

    @GetMapping("books/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }
}
