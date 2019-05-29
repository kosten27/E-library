package com.kostenko.elibrary.controllers;

import com.kostenko.elibrary.exceptions.ValidationException;
import com.kostenko.elibrary.models.BookOrder;
import com.kostenko.elibrary.models.BookOrderLine;
import com.kostenko.elibrary.models.OrderStatus;
import com.kostenko.elibrary.models.Series;
import com.kostenko.elibrary.services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class BookOrderController {

    private BookOrderService bookOrderService;
    private ReaderService readerService;
    private BookService bookService;
    private SeriesService seriesService;
    private BookOrderLineService bookOrderLineService;

    public BookOrderController(BookOrderService bookOrderService, ReaderService readerService, BookService bookService,
                               SeriesService seriesService, BookOrderLineService bookOrderLineService) {
        this.bookOrderService = bookOrderService;
        this.readerService = readerService;
        this.bookService = bookService;
        this.seriesService = seriesService;
        this.bookOrderLineService = bookOrderLineService;
    }

    @GetMapping("/orders")
    public String getBooks(Model model,
                           @RequestParam(name = "page", defaultValue = "1") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "deadline", required = false)
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date deadline) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<BookOrder> orders = null;
        if (deadline != null) {
            orders = bookOrderService.findAllByDeadlineLessThanEqualAndOrderStatusEquals(deadline,
                    pageable, OrderStatus.COMPLETED);
        } else {
            orders = bookOrderService.findPagination(pageable);
        }
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @GetMapping("/orders/add")
    public String addOrder(BookOrder bookOrder, Model model) {
        model.addAttribute("readers", readerService.findAll());
        return "orders/order";
    }

    @PostMapping("/orders/save")
    public String saveOrder(@Valid BookOrder bookOrder, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("readers", readerService.findAll());
            return "orders/order";
        }
        try {
            bookOrderService.save(bookOrder);
        } catch (ValidationException e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("readers", readerService.findAll());
            return "orders/order";
        }
        return "redirect:/orders/" + bookOrder.getId();
    }

    @GetMapping("/orders/{id}")
    public String showOrder(@PathVariable("id") Long id, Model model) {
        BookOrder bookOrder = bookOrderService.findById(id);
        model.addAttribute("bookOrder", bookOrder);
        model.addAttribute("readers", readerService.findAll());
        return "orders/order";
    }

    @GetMapping("/orders/{orderId}/lines/add")
    public String addLine(@PathVariable("orderId") Long orderId, BookOrderLine bookOrderLine, Model model) {
        List<Series> availableSeries = seriesService.findAllAvailable();
        if (availableSeries.size() > 0) {
            model.addAttribute("listSeries", availableSeries);
        } else {
            model.addAttribute("message", "No book series available");
        }
        return "orders/line";
    }

    @PostMapping("/orders/{orderId}/lines/save")
    public String addLine(@PathVariable("orderId") Long orderId, @Valid BookOrderLine bookOrderLine,
                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listSeries", seriesService.findAllAvailable());
            return "orders/line";
        }
        BookOrder bookOrder = bookOrderService.findById(orderId);
        bookOrderLine.setBookOrder(bookOrder);
        bookOrderLine.setBook(bookOrderLine.getSeries().getBook());
        if (bookOrderLine.getId() == null) {
            bookOrder.addLine(bookOrderLine);
        }
        try {
            bookOrderService.save(bookOrder);
        } catch (ValidationException e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("listSeries", seriesService.findAllAvailable());
            return "orders/line";
        }
        return "redirect:/orders/" + orderId;
    }

    @GetMapping("/orders/{orderId}/lines/{lineId}")
    public String showLine(@PathVariable("orderId") Long orderId, @PathVariable("lineId") Long lineId, Model model) {
        model.addAttribute("orderId", orderId);
        BookOrderLine bookOrderLine = bookOrderLineService.findById(lineId);
        model.addAttribute("bookOrderLine", bookOrderLine);
        model.addAttribute("books", bookService.findAll());
        List<Series> availableSeries = seriesService.findAllAvailable();
        if (!availableSeries.contains(bookOrderLine.getSeries())) {
            availableSeries.add(bookOrderLine.getSeries());
        }
        model.addAttribute("listSeries", availableSeries);
        return "orders/line";
    }

    @GetMapping("/orders/{orderId}/lines/{lineId}/delete")
    public String deleteLine(@PathVariable("orderId") Long orderId, @PathVariable("lineId") Long lineId, Model model) {
        bookOrderLineService.deleteById(lineId);
        return "redirect:/orders/" + orderId;
    }
}
