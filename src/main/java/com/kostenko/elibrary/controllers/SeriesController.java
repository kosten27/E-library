package com.kostenko.elibrary.controllers;

import com.kostenko.elibrary.models.Book;
import com.kostenko.elibrary.models.Series;
import com.kostenko.elibrary.services.BookService;
import com.kostenko.elibrary.services.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SeriesController {

    @Autowired
    private SeriesService seriesService;
    @Autowired
    private BookService bookService;

    @GetMapping("/books/{bookId}/series/add")
    public String addSeries(@PathVariable Long bookId, Series series, Model model) {
        model.addAttribute("bookId", bookId);
        return "books/series";
    }

    @PostMapping("/books/{bookId}/series/save")
    public String saveSeries(@PathVariable Long bookId, Series series, Model model) {
        Book book = bookService.findById(bookId);
        series.setBook(book);
        seriesService.save(series);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/books/{bookId}/series/{id}")
    public String showSeries(@PathVariable Long bookId, @PathVariable Long id, Model model) {
        Series series = seriesService.findById(id);
        model.addAttribute("series", series);
        model.addAttribute("bookId", bookId);
        return "books/series";
    }
}
