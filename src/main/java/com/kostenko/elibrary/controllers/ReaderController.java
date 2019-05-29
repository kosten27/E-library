package com.kostenko.elibrary.controllers;

import com.kostenko.elibrary.models.Reader;
import com.kostenko.elibrary.services.ReaderService;
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
public class ReaderController {

    private ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/readers")
    public String getBooks(Model model,
                           @RequestParam(name = "page", defaultValue = "1") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size) {

        Page<Reader> readers = readerService.findPagination(PageRequest.of(page - 1, size));
        model.addAttribute("readers", readers);
        return "readers/list";
    }

    @GetMapping("/readers/add")
    public String showAddReader(Reader reader, Model model) {
        model.addAttribute("readers", readerService.findAll());
        return "readers/reader";
    }

    @PostMapping("/readers/save")
    public String addReader(@Valid Reader reader, BindingResult result) {
        if (result.hasErrors()) {
            return "readers/add";
        }
        readerService.save(reader);
        return "redirect:/readers/" + reader.getId();
    }

    @GetMapping("/readers/{id}")
    public String showUpdateReader(@PathVariable("id") Long id, Model model) {
        model.addAttribute("reader", readerService.findById(id));
        return "readers/reader";
    }
}
