package com.kostenko.elibrary.controllers;

import com.kostenko.elibrary.models.BookOrder;
import com.kostenko.elibrary.models.BookOrderLine;
import com.kostenko.elibrary.services.*;
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
import java.util.Optional;

@Controller
public class BookOrderController {

    @Autowired
    private BookOrderService bookOrderService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BookService bookService;
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private BookOrderLineService bookOrderLineService;

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
        return "orders/order";
    }

    @PostMapping("/orders/save")
    public String addBook(@Valid BookOrder bookOrder, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "orders/add";
        }
        bookOrderService.save(bookOrder);
        return "redirect:/orders/" + bookOrder.getId();
    }

    @GetMapping("/orders/{id}")
    public String showAuthorDetails(@PathVariable("id") Long id, Model model) {
        BookOrder bookOrder = bookOrderService.findById(id);
        model.addAttribute("bookOrder", bookOrder);
        model.addAttribute("readers", readerService.findAll());
        return "orders/order";
    }

    @GetMapping("/orders/{id}/edit")
    public String showUpdateAuthor(@PathVariable("id") Long id, Model model) {
        BookOrder bookOrder = bookOrderService.findById(id);
        model.addAttribute("bookOrder", bookOrder);
        model.addAttribute("readers", readerService.findAll());
        return "orders/update";
    }

    @PostMapping("/orders/{id}/edit")
    public String updateAuthor(@PathVariable("id") Long id, @Valid BookOrder bookOrder, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("bookOrder", bookOrder);
            model.addAttribute("readers", readerService.findAll());
            return "orders/update";
        }
        bookOrderService.save(bookOrder);
        return "redirect:/orders/" + bookOrder.getId();
    }

    @GetMapping("/orders/{orderId}/lines/add")
    public String addLine(@PathVariable("orderId") Long orderId, BookOrderLine bookOrderLine, Model model) {
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("listSeries", seriesService.findAll());
        return "orders/line";
    }

    @PostMapping("/orders/{orderId}/lines/save")
    public String addLine(@PathVariable("orderId") Long orderId, BookOrderLine bookOrderLine) {
        BookOrder bookOrder = bookOrderService.findById(orderId);
        bookOrderLine.setBookOrder(bookOrder);
        bookOrderLineService.save(bookOrderLine);
        return "redirect:/orders/" + orderId;
    }

    @GetMapping("/orders/{orderId}/lines/{lineId}")
    public String showLine(@PathVariable("orderId") Long orderId, @PathVariable("lineId") Long lineId, Model model) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("bookOrderLine", bookOrderLineService.findById(lineId));
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("listSeries", seriesService.findAll());
        return "orders/line";
    }
}
