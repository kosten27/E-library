package com.kostenko.elibrary.controllers;

import com.kostenko.elibrary.exceptions.ValidationException;
import com.kostenko.elibrary.models.BookOrder;
import com.kostenko.elibrary.models.BookOrderLine;
import com.kostenko.elibrary.models.OrderStatus;
import com.kostenko.elibrary.models.Series;
import com.kostenko.elibrary.services.*;
import org.springframework.beans.factory.annotation.Autowired;
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
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam(name = "deadline", required = false)
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date deadline) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
//        Date deadline = null;
//        if (filterDeadline != null) {
//            try {
//                deadline = new SimpleDateFormat("yyyy-MM-dd").parse(filterDeadline);
//            } catch (ParseException e) {
//            }
//        }
        PageRequest pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<BookOrder> orders = null;
        if (deadline != null) {
            orders = bookOrderService.findAllByDeadlineLessThanEqualAndOrderStatusEquals(deadline,
                    pageable, OrderStatus.COMPLETED);
        } else {
            orders = bookOrderService.findPagination(pageable);
        }
        model.addAttribute("deadline", deadline);
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

//    @GetMapping("/orders/{id}/edit")
//    public String showUpdateAuthor(@PathVariable("id") Long id, Model model) {
//        BookOrder bookOrder = bookOrderService.findById(id);
//        model.addAttribute("bookOrder", bookOrder);
//        model.addAttribute("readers", readerService.findAll());
//        return "orders/update";
//    }

//    @PostMapping("/orders/{id}/edit")
//    public String updateAuthor(@PathVariable("id") Long id, @Valid BookOrder bookOrder, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("bookOrder", bookOrder);
//            model.addAttribute("readers", readerService.findAll());
//            return "orders/update";
//        }
//        bookOrderService.save(bookOrder);
//        return "redirect:/orders/" + bookOrder.getId();
//    }

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
