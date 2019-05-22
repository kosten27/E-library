package com.kostenko.elibrary.controllers;

import com.kostenko.elibrary.models.Author;
import com.kostenko.elibrary.models.BookOrder;
import com.kostenko.elibrary.services.BookOrderService;
import com.kostenko.elibrary.services.ReaderService;
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
import java.util.Date;
import java.util.Optional;

@Controller
public class BookOrderController {

    @Autowired
    private BookOrderService bookOrderService;
    @Autowired
    private ReaderService readerService;

    @GetMapping("/orders")
    public String getBooks(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<BookOrder> orders = bookOrderService.findPagination(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @GetMapping("/orders/add")
    public String showAddBook(BookOrder bookOrder, Model model) {
        model.addAttribute("readers", readerService.findAll());
        return "orders/add";
    }

    @PostMapping("/orders/add")
    public String addBook(@Valid BookOrder bookOrder, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "orders/add";
        }
        bookOrder.setDate(new Date());
        bookOrderService.save(bookOrder);
        return "redirect:/orders";
    }

    @GetMapping("/orders/{id}")
    public String showAuthorDetails(@PathVariable("id") long id, Model model) {
        BookOrder bookOrder = bookOrderService.findById(id);
        model.addAttribute("bookOrder", bookOrder);
        return "orders/orderDetails";
    }


    @GetMapping("/orders/{id}/edit")
    public String showUpdateAuthor(@PathVariable("id") long id, Model model) {
        BookOrder bookOrder = bookOrderService.findById(id);
        model.addAttribute("bookOrder", bookOrder);
        model.addAttribute("readers", readerService.findAll());
        return "orders/update";
    }

    @PostMapping("/orders/{id}/edit")
    public String updateAuthor(@PathVariable("id") long id, @Valid BookOrder bookOrder, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("bookOrder", bookOrder);
            model.addAttribute("readers", readerService.findAll());
            return "orders/update";
        }
        bookOrderService.save(bookOrder);
        return "redirect:/orders/" + bookOrder.getId();
    }

}
